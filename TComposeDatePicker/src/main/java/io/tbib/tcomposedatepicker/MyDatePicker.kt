package io.tbib.tcomposedatepicker

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@Composable
internal fun MyDatePicker(config: ConfigDatePicker, onDateSelected:(LocalDate)->Unit, colors : DatePickerColors = DatePickerDefaults.colors()
){
    var pickerDate by rememberSaveable {
         mutableStateOf(LocalDate.now())
     }
          val formattedDate by remember{
              derivedStateOf {
                  DateTimeFormatter.ofPattern(config.dateFormatPattern).format(pickerDate)
              }
          }
    val dateDialogState = rememberMaterialDialogState()


      OutlinedTextField(
          readOnly = true,
          value = formattedDate,
          onValueChange ={},
          interactionSource = remember { MutableInteractionSource() }
              .also { interactionSource ->
                  LaunchedEffect(interactionSource) {
                      interactionSource.interactions.collect { interaction ->
                          if (interaction is PressInteraction.Release) {
                              dateDialogState.show()
                          }
                      }
                  }
              }
      )

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("Ok") {
                onDateSelected(pickerDate)
                dateDialogState.hide()
            }
            negativeButton("Cancel") {
                dateDialogState.hide()
            }
        }
    ) {
        datepicker(
            initialDate = config.initDate,
            yearRange = config.yearRange,
            locale = config.locale,
            title = config.title,
            allowedDateValidator = config.allowedDateValidator,
            colors = colors

        ) {
            pickerDate = it
        }
    }

}