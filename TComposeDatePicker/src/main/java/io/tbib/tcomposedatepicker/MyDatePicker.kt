package io.tbib.tcomposedatepicker

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import io.tbib.tcomposedatepicker.configs.ConfigButtonDialog
import io.tbib.tcomposedatepicker.configs.ConfigDatePicker
import io.tbib.tcomposedatepicker.states.DatePickerState
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@Composable
internal fun MyDatePicker(
    modifier: Modifier,
    config: ConfigDatePicker,
    state: DatePickerState,
    configButtonDialog: ConfigButtonDialog,
    onDateSelected:(LocalDate?)->Unit,
    colors : DatePickerColors,
    inputFieldColors: TextFieldColors,
    shape: CornerBasedShape

){

      val formattedDate by remember{

              derivedStateOf {
                  DateTimeFormatter.ofPattern(config.dateFormatPattern).format(state.pickerDate?: LocalDate.now())

          }

      }
    val dateDialogState = rememberMaterialDialogState()


      OutlinedTextField(
          modifier = modifier,
            shape = shape,
          readOnly = true,
          value = if(state.pickerDate == null)  "" else formattedDate,
          label = config.label,
          textStyle = config.style,
          enabled = config.enable,
          supportingText =config. supportingText,
          leadingIcon =config. leadingIcon,
          trailingIcon =config. trailingIcon,
          prefix = config.prefix,
          suffix = config.suffix,
          placeholder = config.placeholder,
          onValueChange = {},
          colors = inputFieldColors,
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

            positiveButton(
                configButtonDialog.buttonOk,
                textStyle = configButtonDialog.textStyle,
                res= configButtonDialog.res
            ) {

                onDateSelected(state.pickerDate)
                dateDialogState.hide()
            }
            negativeButton(
                configButtonDialog.buttonCancel,
                textStyle = configButtonDialog.textStyle,
                res= configButtonDialog.res
            ) {
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
            state.pickerDate = it
        }
    }

}