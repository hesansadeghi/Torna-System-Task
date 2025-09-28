package com.example.tornasystemiso8583task.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tornasystemiso8583task.R
import com.example.tornasystemiso8583task.adapter.PopularAdapter
import com.example.tornasystemiso8583task.databinding.ActivityMainBinding
import com.example.tornasystemiso8583task.db.ISO8583Entity
import com.example.tornasystemiso8583task.utils.ISOHelper
import com.example.tornasystemiso8583task.utils.copyToClipBoard
import com.example.tornasystemiso8583task.utils.generateAmount
import com.example.tornasystemiso8583task.utils.generateStan
import com.example.tornasystemiso8583task.utils.hideKeyBoard
import com.example.tornasystemiso8583task.utils.setInit
import com.example.tornasystemiso8583task.view_model.ViewModel
import com.imohsenb.ISO8583.enums.FIELDS
import com.imohsenb.ISO8583.utils.StringUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val viewModel: ViewModel by viewModels()


    @Inject
    lateinit var adapter: PopularAdapter


    private var sizeISO8583 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {


            etCardNumber.addTextChangedListener(object : TextWatcher {

                var isManualChange = false

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (isManualChange) {
                        isManualChange = false
                        return
                    }

                    try {
                        val value = s.toString().replace("  ", "")
                        val reverseValue = value.reversed()
                        val finalValue = StringBuilder()

                        for (k in 1..reverseValue.length) {
                            val value = reverseValue[k - 1]
                            finalValue.append(value)
                            if (k % 4 == 0 && k != reverseValue.length) {
                                finalValue.append("  ")
                            }
                        }

                        isManualChange = true

                        etCardNumber.setText(finalValue.reverse().toString())
                        etCardNumber.setSelection(finalValue.length)
                    } catch (_: Exception) {
                        // Do nothing since not a number
                    }
                }

                override fun afterTextChanged(editable: Editable?) {
                    // Do nothing
                }
            })

            etAmount.addTextChangedListener(object : TextWatcher {
                var isManualChange: Boolean = false

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (isManualChange) {
                        isManualChange = false
                        return
                    }

                    try {
                        val value = s.toString().replace(",", "")
                        val reverseValue = java.lang.StringBuilder(value).reverse()
                            .toString()
                        val finalValue = java.lang.StringBuilder()
                        for (k in 1..reverseValue.length) {
                            val `val` = reverseValue[k - 1]
                            finalValue.append(`val`)
                            if (k % 3 == 0 && k != reverseValue.length) {
                                finalValue.append(",")
                            }
                        }
                        isManualChange = true
                        etAmount.setText(finalValue.reverse())
                        etAmount.setSelection(finalValue.length)
                    } catch (_: java.lang.Exception) {
                        // Do nothing since not a number
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })


            btnSubmit.setOnClickListener {

                val cardNumberValue = etCardNumber.text.toString().replace("  ", "")
                val amountValue = etAmount.text.toString().replace(",", "")

                if (cardNumberValue.length != 16 || cardNumberValue.first().toString() == "0") {

                    etCardNumber.error = "لطفا شماره کارت معتبر وارد کنید."
                } else {


                    if (amountValue.isEmpty()) {

                        etAmount.error = "لطفا مبلغی را وارد کنید."
                    } else {


                        if (amountValue.first().toString() == "0") {

                            etAmount.error = "لطفا مبلغ را بدرستی وارد کنید."
                        } else {

                            if (amountValue.toLong() < 100) {

                                etAmount.error = "حداقل مبلغ ورودی صد تومان می باشد."
                            } else {

                                root.hideKeyBoard()


                                val stan = generateStan(sizeISO8583)
                                val amount = generateAmount(amountValue.toLong())

                                val iSOMessage = ISOHelper.buildRequest(
                                    stan,
                                    cardNumberValue,
                                    amount
                                )


                                val entity = ISO8583Entity(
                                    message = iSOMessage.toString(),
                                    f2Pan = iSOMessage.getStringField(FIELDS.F2_PAN),
                                    f4AmountTransaction = iSOMessage.getStringField(FIELDS.F4_AmountTransaction),
                                    f7TransmissionDataTime = iSOMessage.getStringField(FIELDS.F7_TransmissionDataTime),
                                    f11Stan = iSOMessage.getStringField(FIELDS.F11_STAN),
                                    mti = iSOMessage.mti.toString(),
                                    bitmap = StringUtil.fromByteArray(iSOMessage.primaryBitmap)
                                )

                                viewModel.insertIOS8583(entity)

                                etCardNumber.setText("")
                                etAmount.setText("")

                                Toast.makeText(
                                    this@MainActivity,
                                    "ذخیره شد",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    }

                }

            }


            viewModel.gelAllIsoMessage.observe(this@MainActivity) { iSO8583Entities ->

                sizeISO8583 = iSO8583Entities.size


                adapter.setData(iSO8583Entities)
                adapter.setOnClickListener { allMessage ->

                    this@MainActivity.copyToClipBoard(allMessage)

                    Toast.makeText(
                        this@MainActivity,
                        "پیام مورد نظر در کیپ برد ذخیره شد.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                iso8583RecyclerView.setInit(adapter, LinearLayoutManager(this@MainActivity))

                if (iSO8583Entities.isNotEmpty()) {
                    iso8583RecyclerView.scrollToPosition(sizeISO8583 - 1)
                }
            }


        }

    }
}