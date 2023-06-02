package top.chilfish.labs.utils

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun alert(
    context: Context,
    title: String,
    subtitle: String,
    cancel: String,
    confirm: String,
    cancelAction: () -> Unit,
    confirmAction: () -> Unit
): MaterialAlertDialogBuilder {
    return MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(subtitle)
        .setPositiveButton(
            confirm
        ) { _: DialogInterface?, _: Int ->
            confirmAction()
        }
        .setNegativeButton(
            cancel
        ) { _: DialogInterface?, _: Int ->
            cancelAction()
        }
}
