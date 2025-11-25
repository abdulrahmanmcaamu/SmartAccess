package com.example.smartaccess.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartaccess.models.ModuleModel

@Composable
fun ModuleCard(module: ModuleModel, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable { onClick() }
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            Text(text = module.title, style = MaterialTheme.typography.h6)
        }
    }
}
