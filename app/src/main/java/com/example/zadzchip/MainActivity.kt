package com.example.zadzchip

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var shoppingItems: Map<String, List<String>>
    private var selectedItems: MutableList<String> = mutableListOf()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shoppingItems = mapOf(
            "Warzywa" to listOf("Marchewki", "Ziemniaki", "Brokuły", "Sałata"),
            "Wędliny" to listOf("Szynka", "Salami","Bekon"),
            "Pieczywo" to listOf("Bułka", "Bagietka", "Bułka Paryska", "Bagietka Toskańska",)
        )

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.addModeRadioButton -> showAddMode()
                R.id.checkOffModeRadioButton -> showCheckOffMode()
            }
        }

        val vegetablesCheckboxGroup = findViewById<ChipGroup>(R.id.vegetablesCheckboxGroup)
        val MeatsCheckboxGroup = findViewById<ChipGroup>(R.id.MeatsCheckboxGroup)
        val breadCheckboxGroup = findViewById<ChipGroup>(R.id.breadCheckboxGroup)

        vegetablesCheckboxGroup.removeAllViews()
        MeatsCheckboxGroup.removeAllViews()
        breadCheckboxGroup.removeAllViews()

        val shoppingItemList = shoppingItems.values.flatten()
        for (item in shoppingItemList) {
            val checkbox = Chip(this)
            checkbox.text = item
            checkbox.isCheckable = true
            checkbox.tag = shoppingItems.entries.firstOrNull { it.value.contains(item) }?.key


            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItems.add(item)
                } else {
                    selectedItems.remove(item)
                }
            }
            when (checkbox.tag) {
                "Warzywa" -> {
                    checkbox.chipBackgroundColor =
                        resources.getColorStateList(R.color.vegetablesColor)
                }
                "Wędliny" -> {
                    checkbox.chipBackgroundColor =
                        resources.getColorStateList(R.color.MeatsColor)
                }
                "Pieczywo" -> {
                    checkbox.chipBackgroundColor = resources.getColorStateList(R.color.breadColor)
                }
            }
            when (checkbox.tag) {
                "Warzywa" -> vegetablesCheckboxGroup.addView(checkbox)
                "Wędliny" -> MeatsCheckboxGroup.addView(checkbox)
                "Pieczywo" -> breadCheckboxGroup.addView(checkbox)
            }
        }


    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private fun showAddMode() {
        val checkboxesLayout = findViewById<View>(R.id.checkboxesLayout)
        val chipsLayout = findViewById<View>(R.id.chipsLayout)

        checkboxesLayout.visibility = View.VISIBLE
        chipsLayout.visibility = View.GONE
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private fun showCheckOffMode() {
        val checkboxesLayout = findViewById<View>(R.id.checkboxesLayout)
        val chipsLayout = findViewById<View>(R.id.chipsLayout)
        checkboxesLayout.visibility = View.GONE
        chipsLayout.visibility = View.VISIBLE


        val chipsGroup = findViewById<ChipGroup>(R.id.chipsGroup)
        chipsGroup.removeAllViews()


        for ((groupName, items) in shoppingItems) {
            for (item in items) {
                if (selectedItems.contains(item)) {
                    val chip = Chip(this)
                    chip.text = item
                    chip.isCloseIconVisible = true
                    chip.tag = groupName
                    chipsGroup.addView(chip)
                    when (groupName) {
                        "Warzywa" -> {
                            chip.chipBackgroundColor =
                                resources.getColorStateList(R.color.vegetablesColor)
                        }
                        "Wędliny" -> {
                            chip.chipBackgroundColor =
                                resources.getColorStateList(R.color.MeatsColor)
                        }
                        "Pieczywo" -> {
                            chip.chipBackgroundColor =
                                resources.getColorStateList(R.color.breadColor)
                        }
                    }

                    chip.setOnCloseIconClickListener {
                        selectedItems.remove(item)
                        chipsGroup.removeView(chip)
                    }

                }
            }
        }
    }
}