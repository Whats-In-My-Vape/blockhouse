package org.wit.blockhouse.models.firebase

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.wit.blockhouse.helpers.readImageFromPath
import org.wit.blockhouse.models.BlockhouseModel
import org.wit.blockhouse.models.BlockhouseStore
import org.jetbrains.anko.AnkoLogger
import java.io.ByteArrayOutputStream
import java.io.File
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.R.string.no
import android.R.attr.name


class BlockhouseFireStore(val context: android.content.Context) : BlockhouseStore, AnkoLogger {

    val blockhouses = ArrayList<BlockhouseModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override fun findAll(): List<BlockhouseModel> {
        return blockhouses
    }

    override fun findById(id: Long): BlockhouseModel? {
        val foundBlockhouse: BlockhouseModel? = blockhouses.find { p -> p.id == id }
        return foundBlockhouse
    }

    override fun create(blockhouse: BlockhouseModel) {
        val key = db.child("users").child(userId).child("blockhouses").push().key
        key?.let {
            blockhouse.fbId = key
            blockhouses.add(blockhouse)
            db.child("users").child(userId).child("blockhouses").child(key).setValue(blockhouse)
            updateImage(blockhouse)
        }
    }

    override fun update(blockhouse: BlockhouseModel) {
        var foundBlockhouse: BlockhouseModel? = blockhouses.find { p -> p.fbId == blockhouse.fbId }
        if (foundBlockhouse != null) {
            foundBlockhouse.title = blockhouse.title
            foundBlockhouse.description = blockhouse.description
            foundBlockhouse.image = blockhouse.image
            foundBlockhouse.location = blockhouse.location
            foundBlockhouse.favourite = blockhouse.favourite
            foundBlockhouse.rating = blockhouse.rating
        }

        db.child("users").child(userId).child("blockhouses").child(blockhouse.fbId)
            .setValue(blockhouse)
        if ((blockhouse.image.length) > 0 && (blockhouse.image[0] != 'h')) {
            updateImage(blockhouse)
        }
    }

    override fun delete(blockhouse: BlockhouseModel) {
        db.child("users").child(userId).child("blockhouses").child(blockhouse.fbId).removeValue()
        blockhouses.remove(blockhouse)
    }

    override fun clear() {
        blockhouses.clear()
    }

    fun updateImage(blockhouse: BlockhouseModel) {
        if (blockhouse.image != "") {
            val fileName = File(blockhouse.image)
            val imageName = fileName.getName()

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, blockhouse.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        blockhouse.image = it.toString()
                        db.child("users").child(userId).child("blockhouses").child(blockhouse.fbId)
                            .setValue(blockhouse)
                    }
                }
            }
        }
    }

    fun fetchBlockhouses(blockhousesReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(blockhouses) {
                    it.getValue<BlockhouseModel>(
                        BlockhouseModel::class.java
                    )
                }
                blockhousesReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        blockhouses.clear()
        db.child("users").child(userId).child("blockhouses")
            .addListenerForSingleValueEvent(valueEventListener)

    }

}
