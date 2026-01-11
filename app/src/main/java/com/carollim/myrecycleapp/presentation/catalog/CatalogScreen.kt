package com.carollim.myrecycleapp.presentation.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.carollim.myrecycleapp.domain.repository.CatalogItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Recycling Catalog") }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${state.error}", color = MaterialTheme.colorScheme.error)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(padding)
            ) {
                items(state.items) { item ->
                    CatalogItemCard(item = item)
                }
            }
        }
    }
}

@Composable
fun CatalogItemCard(item: CatalogItem) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().height(240.dp)
    ) {
        Column {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier.fillMaxWidth().height(140.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "RM ${String.format("%.2f", item.pricePerKg)} / kg",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.desc,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
