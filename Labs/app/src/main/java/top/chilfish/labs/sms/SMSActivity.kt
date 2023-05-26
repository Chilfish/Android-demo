package top.chilfish.labs.sms

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.chilfish.labs.BaseActivity
import top.chilfish.labs.databinding.ActivitySmsBinding

class SMSActivity : BaseActivity() {

    private val smsPermissionCode = 101
    private val smsContentUri = Uri.parse("content://sms/")
    private val COUNT = 10

    private lateinit var binding: ActivitySmsBinding
    private lateinit var rv: RecyclerView
    private val smsAdapter = SMSAdapter()

    private fun init() {
        binding = ActivitySmsBinding.inflate(layoutInflater)
        rv = binding.listSms
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = smsAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_SMS),
                    smsPermissionCode
                )
            } else {
                readSms()
            }
        }
    }

    private fun readSms() {
        val cursor = contentResolver.query(
            smsContentUri, null, null, null, null
        )
        var i = 0
        cursor?.let {
            if (it.moveToFirst()) {
                val bodyIndex = it.getColumnIndex("body")
                val addressIndex = it.getColumnIndex("address")
                do {
                    val body = it.getString(bodyIndex)
                    val address = it.getString(addressIndex)
                    smsAdapter.addItem(SMSEntity(address, body))
                } while (it.moveToNext() && i++ < COUNT)
            }
            it.close()
        }
    }
}