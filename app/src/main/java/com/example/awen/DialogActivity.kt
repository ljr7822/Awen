package com.example.awen

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.awen.commont.CommonDialogFragment.OnDialogCancelListener
import com.example.awen.commont.DialogFragmentHelper
import com.example.awen.commont.IDialogResultListener
import java.util.*


/**
 * 弹窗的Activity
 */
class DialogActivity : AppCompatActivity() {

    private lateinit var mDialogFragment: Unit
    private var mShowProgress: DialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

    }

    /**
     * 创建菜单
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.menu_dialog, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.showConfirmDialog -> showConfirmDialog()
            R.id.showDateDialog -> showDateDialog()
            R.id.showInsertDialog -> showInsertDialog()
            //R.id.showIntervalInsertDialog -> showIntervalInsertDialog()
            R.id.showListDialog -> showListDialog()
            R.id.showPasswordInsertDialog -> showPasswordInsertDialog()
            R.id.showProgress -> mShowProgress =
                DialogFragmentHelper.showProgress(
                    supportFragmentManager,
                    message = "正在加载中",
                    cancelListener = null
                )
            R.id.showTimeDialog -> showTimeDialog()
            R.id.showTips -> DialogFragmentHelper.showTips(
                supportFragmentManager,
                message = "你进入了无网的异次元中",
                cancelListener = null
            )
            else -> {
            }
        }
        return true
    }

    /**
     * 选择时间的弹出窗
     */
    private fun showTimeDialog() {
        val titleTime = "请选择时间"
        val calendarTime: Calendar = Calendar.getInstance()
        DialogFragmentHelper.showTimeDialog(
            fragmentManager = supportFragmentManager,
            title = titleTime,
            calendar = calendarTime,
            resultListener = object : IDialogResultListener<Calendar> {
                override fun onDataResult(result: Calendar) {
                    showToast(result.time.date.toString())
                }
            },
            cancelable = true
        )
    }

    /**
     * 输入密码的弹出窗
     */
    private fun showPasswordInsertDialog() {
        val titlePassword = "请输入密码"
        DialogFragmentHelper.showPasswordInsertDialog(
            supportFragmentManager,
            title = titlePassword,
            resultListener = object : IDialogResultListener<String> {
                override fun onDataResult(result: String) {
                    showToast("密码为：$result")
                }
            }, cancelable = true
        )
    }

    /**
     * 显示列表的弹出窗
     */
    private fun showListDialog() {
        val titleList = "选择哪种方向？"
        val language = arrayOf("Android", "iOS", "web 前端", "Web 后端", "老子不打码了")
        DialogFragmentHelper.showListDialog(
            supportFragmentManager,
            titleList,
            language,
            resultListener = object : IDialogResultListener<Int> {
                override fun onDataResult(result: Int) {
                    showToast(language[result])
                }
            }, cancelable = true
        )
    }

    /**
     * 选择日期的弹出窗
     */
    private fun showDateDialog() {
        val titleDate = "请选择日期"
        val calendar = Calendar.getInstance()
        mDialogFragment = DialogFragmentHelper.showDateDialog(
            fragmentManager = supportFragmentManager,
            title = titleDate,
            calendar = calendar,
            resultListener = object : IDialogResultListener<Calendar> {
                override fun onDataResult(result: Calendar) {
                    showToast(java.lang.String.valueOf(result.time.date))
                }
            },
            cancelable = true
        )
    }

    /**
     * 确认和取消的弹出窗
     */
    private fun showConfirmDialog() {
        DialogFragmentHelper.showConfirmDialog(
            fragmentManager = supportFragmentManager,
            message = "是否选择 Android？",
            listener = object : IDialogResultListener<Int> {
                override fun onDataResult(result: Int) {
                    showToast("You Click Ok")
                }
            },
            cancelable =  true,
            cancelListener = object : OnDialogCancelListener {
                override fun onCancel() {
                    showToast("You Click Cancel")
                }
            })
    }

    private fun showInsertDialog() {
        val titleInsert = "请输入想输入的内容"
        DialogFragmentHelper.showInsertDialog(
            fragmentManager = supportFragmentManager,
            title = titleInsert,
            resultListener = object : IDialogResultListener<String> {
                override fun onDataResult(result: String) {
                    showToast(result)
                }
            }, cancelable = true)
    }

    /**
     * 对 Toast 进行封藏，减少代码量
     *
     * @param message 想要显示的信息
     */
    private fun showToast(message: kotlin.String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}