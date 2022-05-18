package com.example.awen.commont

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.text.InputType
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.example.awen.R
import com.example.awen.commont.CommonDialogFragment.OnCallDialog
import com.example.awen.commont.CommonDialogFragment.OnDialogCancelListener
import java.util.*


/**
 *
 * @Author: iwen
 * @CreateDate: 2022/5/18
 * @Package: com.example.awen.commont
 */
class DialogFragmentHelper {

    companion object {
        private const val TAG = "DialogFragmentHelper"
        private const val DIALOG_POSITIVE = "确定"
        private const val DIALOG_NEGATIVE = "取消"

        /**
         * 加载中的弹出窗
         */
        private val PROGRESS_THEME: Int = R.style.Base_AlertDialog
        private val PROGRESS_TAG: String = TAG + ":progress"

        /**
         * 简单提示弹出窗
         */
        private val TIPS_THEME: Int = R.style.Base_AlertDialog
        private val TIPS_TAG: String = TAG + ":tips"

        /**
         * 确定取消框
         */
        private val CONFIRM_THEME: Int = R.style.Base_AlertDialog
        private val CONfIRM_TAG: String = TAG + ":confirm"

        /**
         * 带列表的弹出窗
         */
        private val LIST_THEME: Int = R.style.Base_AlertDialog
        private val LIST_TAG: String = TAG + ":list"

        /**
         * 选择日期
         */
        private val DATE_THEME: Int = R.style.Base_AlertDialog
        private val DATE_TAG: String = TAG + ":date"

        /**
         * 选择时间
         */
        private val TIME_THEME: Int = R.style.Base_AlertDialog
        private val TIME_TAG: String = TAG + ":time"

        /**
         * 带输入框的弹出窗
         */
        private val INSERT_THEME: Int = R.style.Base_AlertDialog
        private val INSERT_TAG: String = TAG + ":insert"

        /**
         * 带输入密码框的弹出窗
         */
        private val PASSWORD_INSER_THEME: Int = R.style.Base_AlertDialog
        private val PASSWORD_INSERT_TAG: String = TAG + ":insert"

        /**
         * 两个输入框的弹出窗
         */
        private val INTERVAL_INSERT_THEME: Int = R.style.Base_AlertDialog
        private val INTERVAL_INSERT_TAG: String = TAG + ":interval_insert"

        /**
         * 正在加载dialog
         */
        fun showProgress(
            fragmentManager: FragmentManager,
            message: String,
            cancelable: Boolean = true,
            cancelListener: OnDialogCancelListener?
        ): CommonDialogFragment {
            val dialogFragment = CommonDialogFragment.newInstance(object : OnCallDialog {
                override fun getDialog(context: Context): Dialog {
                    val progressDialog = ProgressDialog(context, PROGRESS_THEME)
                    progressDialog.setMessage(message)
                    return progressDialog
                }
            }, cancelable = cancelable, cancelListener = cancelListener)
            dialogFragment.show(fragmentManager, PROGRESS_TAG)
            return dialogFragment
        }

        /**
         * 简单提示dialog
         */
        fun showTips(
            fragmentManager: FragmentManager,
            message: String,
            cancelable: Boolean = true,
            cancelListener: OnDialogCancelListener?
        ) {
            val dialogFragment = CommonDialogFragment.newInstance(object : OnCallDialog {
                override fun getDialog(context: Context): Dialog {
                    val builder = AlertDialog.Builder(context, TIPS_THEME)
                    builder.setMessage(message)
                    return builder.create()
                }
            }, cancelable = cancelable, cancelListener = cancelListener)
            dialogFragment.show(fragmentManager, TIPS_TAG)
        }

        /**
         * 确定取消dialog
         */
        fun showConfirmDialog(
            fragmentManager: FragmentManager,
            message: String,
            cancelable: Boolean,
            listener: IDialogResultListener<Int>?,
            cancelListener: OnDialogCancelListener?
        ) {
            val dialogFragment = CommonDialogFragment.newInstance(object : OnCallDialog {
                override fun getDialog(context: Context): Dialog {
                    val builder = AlertDialog.Builder(context, CONFIRM_THEME)
                    builder.setMessage(message)
                    builder.setPositiveButton(
                        DIALOG_POSITIVE
                    ) { p0, which -> listener?.onDataResult(which) }
                    builder.setNegativeButton(
                        DIALOG_NEGATIVE
                    ) { p0, which -> listener?.onDataResult(which) }
                    return builder.create()
                }
            }, cancelable = cancelable, cancelListener = cancelListener)
            dialogFragment.show(fragmentManager, CONfIRM_TAG);
        }

        /**
         * 带列表的弹出窗
         */
        fun showListDialog(
            fragmentManager: FragmentManager,
            title: String,
            items: Array<String>,
            cancelable: Boolean,
            resultListener: IDialogResultListener<Int>?
        ) {
            val dialogFragment = CommonDialogFragment.newInstance(object : OnCallDialog {
                override fun getDialog(context: Context): Dialog {
                    val builder = AlertDialog.Builder(context, LIST_THEME)
                    builder.setTitle(title)
                    builder.setItems(
                        items
                    ) { p0, which -> resultListener?.onDataResult(which) }
                    return builder.create()
                }
            }, cancelable = cancelable, null)
            dialogFragment.show(fragmentManager, LIST_TAG)
        }

        /**
         * 选择日期
         */
        fun showDateDialog(
            fragmentManager: FragmentManager,
            title: String,
            calendar: Calendar,
            cancelable: Boolean,
            resultListener: IDialogResultListener<Calendar>?
        ) {
            val dialogFragment = CommonDialogFragment.newInstance(object : OnCallDialog {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun getDialog(context: Context): Dialog {
                    val datePickerDialog = DatePickerDialog(
                        context,
                        DATE_THEME,
                        { p0, year, month, dayOfMonth ->
                            calendar.set(year, month, dayOfMonth);
                            resultListener?.onDataResult(calendar);
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    datePickerDialog.setTitle(title)
                    datePickerDialog.setOnShowListener {
                        datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).text =
                            DIALOG_POSITIVE;
                        datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).text =
                            DIALOG_NEGATIVE;
                    }
                    return datePickerDialog
                }
            }, cancelable = cancelable, null)
            dialogFragment.show(fragmentManager, DATE_TAG)
        }

        /**
         * 选择时间
         */
        fun showTimeDialog(
            fragmentManager: FragmentManager,
            title: String,
            calendar: Calendar,
            cancelable: Boolean,
            resultListener: IDialogResultListener<Calendar>?
        ) {
            val dialogFragment = CommonDialogFragment.newInstance(object : OnCallDialog {
                override fun getDialog(context: Context): Dialog {
                    val dateDialog = TimePickerDialog(
                        context,
                        TIME_THEME,
                        { p0, hourOfDay, minute ->
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);
                            resultListener?.onDataResult(calendar);
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    )
                    dateDialog.setTitle(title)
                    dateDialog.setOnShowListener {
                        dateDialog.getButton(DialogInterface.BUTTON_POSITIVE).text = DIALOG_POSITIVE
                        dateDialog.getButton(DialogInterface.BUTTON_NEGATIVE).text = DIALOG_NEGATIVE
                    }
                    return dateDialog
                }

            }, cancelable = cancelable, null)
            dialogFragment.show(fragmentManager, TIME_TAG)
        }

        /**
         * 带输入框的弹出窗
         */
        fun showInsertDialog(
            fragmentManager: FragmentManager,
            title: String,
            cancelable: Boolean,
            resultListener: IDialogResultListener<String>?
        ) {
            val dialogFragment = CommonDialogFragment.newInstance(object : OnCallDialog {
                override fun getDialog(context: Context): Dialog {
                    val editText = EditText(context)
                    editText.background = null
                    editText.setPadding(60, 40, 0, 0)
                    val dialog = AlertDialog.Builder(context, INSERT_THEME)
                    dialog.setTitle(title)
                    dialog.setView(editText)
                    dialog.setPositiveButton(DIALOG_POSITIVE) { p0, p1 ->
                        resultListener?.onDataResult(
                            editText.text.toString()
                        )
                    }
                    dialog.setNegativeButton(DIALOG_NEGATIVE, null)
                    return dialog.create()
                }
            }, cancelable = cancelable, null)
            dialogFragment.show(fragmentManager, INSERT_TAG)
        }

        /**
         * 带输入密码框的弹出窗
         */
        fun showPasswordInsertDialog(
            fragmentManager: FragmentManager,
            title: String,
            cancelable: Boolean,
            resultListener: IDialogResultListener<String>?
        ) {
            val dialogFragment = CommonDialogFragment.newInstance(object : OnCallDialog {
                override fun getDialog(context: Context): Dialog {
                    val editText = EditText(context)
                    editText.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    editText.isEnabled = true
                    val dialog = AlertDialog.Builder(context, PASSWORD_INSER_THEME)
                    dialog.setTitle(title)
                    dialog.setView(editText)
                    dialog.setPositiveButton(
                        DIALOG_POSITIVE
                    ) { p0, p1 -> resultListener?.onDataResult(editText.text.toString()) }
                    dialog.setNegativeButton(DIALOG_NEGATIVE, null)
                    return dialog.create()
                }
            }, cancelable = cancelable, null)
            dialogFragment.show(fragmentManager, INSERT_TAG);
        }
    }
}