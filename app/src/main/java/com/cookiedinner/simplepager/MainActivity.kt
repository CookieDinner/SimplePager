package com.cookiedinner.simplepager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cookiedinner.simple_pager.PagerConfig
import com.cookiedinner.simple_pager.items
import com.cookiedinner.simplepager.ui.theme.SimplePagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    val viewModel = remember { PagerViewModel() }
    SimplePagerTheme {
        AppContent(viewModel = viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(viewModel: PagerViewModel) {
    val mocks by viewModel.mocks.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.beginPaging()
    }

    var pageSize by rememberSaveable {
        mutableStateOf("2")
    }
    var prefetchDistance by rememberSaveable {
        mutableStateOf("1")
    }
    var firstPageIndex by rememberSaveable {
        mutableStateOf("0")
    }
    var distinctItems by rememberSaveable {
        mutableStateOf(true)
    }
    var deactivatePagerOnEndReached by rememberSaveable {
        mutableStateOf(true)
    }
    var dirtyList by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(pageSize, prefetchDistance, firstPageIndex, distinctItems, deactivatePagerOnEndReached) {
        lazyListState.scrollToItem(0)
        viewModel.changePagerConfig(
            PagerConfig(
                pageSize = pageSize.toIntOrNull() ?: 1,
                prefetchDistance = prefetchDistance.toIntOrNull() ?: 1,
                firstPageIndex = firstPageIndex.toIntOrNull() ?: 0,
                onlyDistinctItems = distinctItems,
                deactivatePagerOnEndReached = deactivatePagerOnEndReached

            )
        )
    }

    LaunchedEffect(dirtyList) {
        viewModel.changeMockListType(dirtyList)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Simple Pager Demo")
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.beginPaging()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh pager"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = pageSize,
                    onValueChange = {
                        pageSize = it
                    },
                    label = {
                        Text(text = "Page size")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = prefetchDistance,
                    onValueChange = {
                        prefetchDistance = it
                    },
                    label = {
                        Text(text = "Prefetch distance")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = firstPageIndex,
                    onValueChange = {
                        firstPageIndex = it
                    },
                    label = {
                        Text(text = "First page index")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Distinct items")
                    Switch(
                        checked = distinctItems,
                        onCheckedChange = {
                            distinctItems = it
                        }
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Pager off on end")
                    Switch(
                        checked = deactivatePagerOnEndReached,
                        onCheckedChange = {
                            deactivatePagerOnEndReached = it
                        }
                    )
                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Dirty list")
                    Switch(
                        checked = dirtyList,
                        onCheckedChange = {
                            dirtyList = it
                        }
                    )
                }
            }
            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(mocks) {
                    OutlinedCard {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .height(64.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = it.id.toString())
                            Text(text = it.name)
                            Text(text = it.value.toString())
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App()
}