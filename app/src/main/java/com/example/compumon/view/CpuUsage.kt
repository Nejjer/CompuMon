package com.example.compumon.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProgressWithTitle(
    title: String,
    currentValue: Int,
    maxValue: Int,
    unit: String
) {
    val progress = currentValue.toFloat() / maxValue.toFloat()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(150.dp)
        ) {
            CircularProgressIndicator(
                progress = { progress },
                strokeWidth = 8.dp,
                modifier = Modifier.fillMaxSize()
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "$currentValue",
                    style = MaterialTheme.typography.titleLarge, // Большой шрифт для числа
                )
                Spacer(modifier = Modifier.width(4.dp)) // Немного отступа между числом и единицей
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodySmall, // Маленький шрифт для единицы измерения
                )
            }
        }
    }
}


@Composable
fun GridOfProgress() {
    // Сетка с двумя столбцами
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Указываем два столбца
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp), // Отступы по горизонтали
        verticalArrangement = Arrangement.spacedBy(16.dp)   // Отступы по вертикали
    ) {
        items(6) { index ->
            // Пример значений для нескольких элементов
            ProgressWithTitle(
                title = "Загрузка $index",
                currentValue = (index + 1) * 20,
                maxValue = 100,
                unit = "МБ"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GridOfProgressPreview() {
    GridOfProgress()
}