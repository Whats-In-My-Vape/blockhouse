/*
package org.wit.blockhouse.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.MapsInitializer
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.main_layout.description
import kotlinx.android.synthetic.main.main_layout.blockhouseTitle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.blockhouse.R
import org.wit.blockhouse.adapters.ImageAdapter
import org.wit.blockhouse.adapters.NotesAdapter
import org.wit.blockhouse.adapters.NoteListener
import org.wit.blockhouse.adapters.ImageListener
import org.wit.blockhouse.helpers.readImageFromPath
import org.wit.blockhouse.helpers.showImagePicker
import org.wit.blockhouse.main.MainApp
import org.wit.blockhouse.models.Location
import org.wit.blockhouse.models.BlockhouseModel
import java.lang.Exception
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class BlockhouseActivity : AppCompatActivity(), AnkoLogger, NoteListener, ImageListener {

    var blockhouse = BlockhouseModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    var location = Location(52.245696, -7.139102, 15f)
    var date = String()
    var edit = false
    var dateFormat = SimpleDateFormat("dd MMM, YYYY", Locale.UK)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp

        // ------------- Select Image Button  ------------- //
        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }


        // ------------- Select Location Button  ------------- //
        with(map) {
            onCreate(null)
            getMapAsync {
                MapsInitializer.initialize(applicationContext)
            }
        }

        blockhouseLocation.setOnClickListener {
            if (blockhouse.location.lat == 0.0 && blockhouse.location.lng == 0.0) {
                startActivityForResult(
                    intentFor<MapActivity>().putExtra("location", location),
                    LOCATION_REQUEST
                )
            } else {
                startActivityForResult(
                    intentFor<MapActivity>().putExtra(
                        "location",
                        blockhouse.location
                    ), LOCATION_REQUEST
                )
            }
        }

        setDate.visibility = View.INVISIBLE
        dateText.visibility = View.INVISIBLE

        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                setDate.visibility = View.VISIBLE
                dateText.visibility = View.VISIBLE
                blockhouse.check_box = true
            } else {
                setDate.visibility = View.INVISIBLE
                blockhouse.check_box = false
            }
        }

        // ------------- Date Picker Dialog  ------------- //
        // Tutorial from https://youtu.be/gollUUFBKQA //
        setDate.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()

                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    date = dateFormat.format(selectedDate.time)

                    toast(date)
                    dateText.setText("Date Visited: ${date}")
                },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        val layoutManager = LinearLayoutManager(this)
        note_view.layoutManager = layoutManager
        note_view.adapter = NotesAdapter(blockhouse.note, this)
        loadNotes()

        add_note.setOnClickListener {
            if (noteContent.text.toString().isNotEmpty()) {
                blockhouse.note += noteContent.text.toString()
                note_view.adapter = NotesAdapter(blockhouse.note, this)
                noteContent.setText("")
            } else {
                toast("NOTE EMPTY")
            }
        }

        val imageLayoutManager = LinearLayoutManager(this)
        image_view.layoutManager = imageLayoutManager
        image_view.adapter = ImageAdapter(blockhouse.image_list, this)
        loadImage()

        if (intent.hasExtra("blockhouse_edit")) {
            edit = true
            toast("!! WARNING YOU ARE NOW IN EDIT MODE !!")

            blockhouse = intent.extras?.getParcelable("blockhouse_edit")!!

            blockhouseTitle.setText(blockhouse.title)
            description.setText(blockhouse.description)
            checkBox.isChecked = blockhouse.check_box
            dateText.text = blockhouse.date
            loadNotes()
            loadImage()

            */
/*if (blockhouse.image_list.size == 0) {
                blockhouseImage.setImageResource(R.drawable.splash)
            } else {
                blockhouseImage.setImageBitmap(readImageFromPath(this, blockhouse.image))
            }
*//*

            location = blockhouse.location
            lat.setText("LAT: ${DecimalFormat("#.##").format(location.lat)}")
            lng.setText("LNG: ${DecimalFormat("#.##").format(location.lng)}")

            val latLng = LatLng(blockhouse.location.lat, blockhouse.location.lng)
            map.getMapAsync {
                setMapLocation(it, latLng)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_blockhouse, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> startActivity(
                Intent(
                    this@BlockhouseActivity,
                    BlockhouseListActivity::class.java
                )
            )
            R.id.btnAdd -> {
                blockhouse.title = blockhouseTitle.text.toString()
                blockhouse.description = description.text.toString()
                blockhouse.check_box = checkBox.isChecked
                blockhouse.date = date
                blockhouse.location = location

                if (blockhouse.title.isEmpty() || blockhouse.description.isEmpty()) {
                    toast("Add Failed -> Please enter Blockhouse details next time")
                } else {
                    if (edit) {
                        app.bl
                        toast("UPDATING ....")
                    } else {
                        try {
                            app.users.createBlockhouse(app.currentUser, blockhouse)
                            toast("NEW BLOCKHOUSE ADDED")
                        } catch (e: Exception) {
                            toast("FAILED TO ADD BLOCKHOUSE")
                        }
                    }
                }
                setResult(Activity.RESULT_OK)
                finish()

                startActivity(Intent(this@BlockhouseActivity, BlockhouseListActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    blockhouse.image = data.getData().toString()


                    blockhouse.image_list += blockhouse.image_list
                    image_view.adapter = ImageAdapter(blockhouse.image_list, this)
                } else {
                    toast("ERROR IMAGE")
                }
            }

            LOCATION_REQUEST -> {
                if (data != null) {
                    location = data.extras?.getParcelable("location")!!
                    val lt = location.lat
                    val lg = location.lng

                    lat.setText("LAT: ${DecimalFormat("#.##").format(lt)}")
                    lng.setText("LNG: ${DecimalFormat("#.##").format(lg)}")
                }
            }
        }
    }

    private fun loadNotes() {
        val userBlockhouse = app.users.findOneBlockhouse(app.currentUser, blockhouse.id)
        val notes = userBlockhouse?.note

        if (notes != null) {
            showNotes(notes)
        }
    }

    private fun loadImage() {
        val userBlockhouse = app.users.findOneBlockhouse(app.currentUser, blockhouse.id)
        val img = userBlockhouse?.image_list

        if (img != null) {
            showImages(img)
        }
    }

    private fun showNotes(notes: List<String>) {
        note_view.adapter = NotesAdapter(notes, this)
        note_view.adapter?.notifyDataSetChanged()
    }

    private fun showImages(img: List<String>) {
        image_view.adapter = NotesAdapter(img, this)
        image_view.adapter?.notifyDataSetChanged()
    }

    override fun delNote(removeIndex: Int) {
        blockhouse.note = blockhouse.note.filterIndexed { i, _ -> i != removeIndex }
        note_view.adapter = NotesAdapter(blockhouse.note, this)
    }

    override fun delImg(removeIndex: Int) {
        blockhouse.image_list = blockhouse.image_list.filterIndexed { i, _ -> i != removeIndex }
        image_view.adapter = ImageAdapter(blockhouse.image_list, this)
    }

    // Map methods
    public override fun onResume() {
        map.onResume()
        super.onResume()
    }

    public override fun onPause() {
        super.onPause()
        map.onPause()
    }

    public override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }

    private fun setMapLocation(map: GoogleMap, location: LatLng) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5f))
        with(map) {
            addMarker(
                MarkerOptions().position(
                    location
                )
            )
            mapType = GoogleMap.MAP_TYPE_NORMAL
        }
    }
}

*/
