package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CategoryCapcut
import com.example.ui.theme.CategoryPhotoshop
import com.example.ui.theme.CategoryVideography
import com.example.ui.viewmodel.LoginUiState
import com.example.ui.viewmodel.MasterclassViewModel

@Composable
fun LoginScreen(
    viewModel: MasterclassViewModel,
    modifier: Modifier = Modifier
) {
    val loginState by viewModel.loginUiState.collectAsState()
    val activeSheetUrl by viewModel.sheetUrl.collectAsState()

    var emailInput by remember { mutableStateOf("magicpsd7@gmail.com") }
    var pinInput by remember { mutableStateOf("123456") }
    var showSheetSettingsDialog by remember { mutableStateOf(false) }
    var customSheetUrlInput by remember { mutableStateOf(activeSheetUrl) }

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF090D16))
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Brand Emblem Header
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                CategoryPhotoshop,
                                MaterialTheme.colorScheme.primary,
                                CategoryVideography
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Brush,
                    contentDescription = "Creative Logo",
                    tint = Color.White,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Creative Masterclass",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Photoshop • Mobile Videography • CapCut • Content Creation",
                color = Color(0xFF9CA3AF),
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Google Sheet Verification Badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFF13231F))
                    .clickable { showSheetSettingsDialog = true }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.TableChart,
                        contentDescription = "Sheet Auth",
                        tint = Color(0xFF10B981),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Verified via Google Sheet Enrollment",
                        color = Color(0xFF34D399),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Configure Sheet",
                        tint = Color(0xFF6EE7B7),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Error display card
            if (loginState is LoginUiState.Error) {
                val errorMsg = (loginState as LoginUiState.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF3B1219))
                        .padding(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = "Error",
                            tint = Color(0xFFF87171),
                            modifier = Modifier.size(20.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Authentication Failed",
                                color = Color(0xFFFCA5A5),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = errorMsg,
                                color = Color(0xFFFEE2E2),
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
            }

            // Input Fields Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF111827))
                    .padding(20.dp)
            ) {
                Text(
                    text = "Student Login",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Enter your email as registered in the instructor's sheet",
                    color = Color(0xFF6B7280),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
                )

                // Email Field
                OutlinedTextField(
                    value = emailInput,
                    onValueChange = {
                        emailInput = it
                        if (loginState is LoginUiState.Error) viewModel.clearLoginError()
                    },
                    label = { Text("Registered Email") },
                    placeholder = { Text("e.g. magicpsd7@gmail.com") },
                    leadingIcon = {
                        Icon(Icons.Default.Mail, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color(0xFF374151),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .testTag("login_email_input")
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                // PIN / Passcode Field
                OutlinedTextField(
                    value = pinInput,
                    onValueChange = {
                        pinInput = it
                        if (loginState is LoginUiState.Error) viewModel.clearLoginError()
                    },
                    label = { Text("Access Passcode / PIN") },
                    placeholder = { Text("Default: 123456") },
                    leadingIcon = {
                        Icon(Icons.Default.Key, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color(0xFF374151),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .testTag("login_pin_input")
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Login Button
                Button(
                    onClick = {
                        viewModel.login(emailInput, pinInput)
                    },
                    enabled = loginState !is LoginUiState.Loading,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .testTag("login_submit_button")
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    if (loginState is LoginUiState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Checking Google Sheet...", fontSize = 15.sp)
                    } else {
                        Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Verify Enrollment & Login",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Quick demo accounts test pills
            Text(
                text = "Tap to autofill demo student from sheet:",
                color = Color(0xFF9CA3AF),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DemoAccountCard(
                    email = "magicpsd7@gmail.com",
                    pin = "123456",
                    title = "Your Account (magicpsd7@gmail.com)",
                    tier = "VIP All-Access",
                    onSelect = {
                        emailInput = "magicpsd7@gmail.com"
                        pinInput = "123456"
                    }
                )

                DemoAccountCard(
                    email = "student@creativestudio.com",
                    pin = "editor123",
                    title = "Jordan Tyler",
                    tier = "VIP All-Access",
                    onSelect = {
                        emailInput = "student@creativestudio.com"
                        pinInput = "editor123"
                    }
                )

                DemoAccountCard(
                    email = "photoshop.learner@gmail.com",
                    pin = "psd2026",
                    title = "Sarah Jenkins",
                    tier = "Photoshop & CapCut",
                    onSelect = {
                        emailInput = "photoshop.learner@gmail.com"
                        pinInput = "psd2026"
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Sheet Settings Dialog
        if (showSheetSettingsDialog) {
            AlertDialog(
                onDismissRequest = { showSheetSettingsDialog = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.TableChart, contentDescription = null, tint = Color(0xFF10B981))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Google Sheet Source", color = Color.White, fontSize = 18.sp)
                    }
                },
                text = {
                    Column {
                        Text(
                            text = "Students are authenticated in real time against your Google Sheet. You can paste your published Google Sheet CSV URL or leave the default.",
                            color = Color(0xFFD1D5DB),
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = customSheetUrlInput,
                            onValueChange = { customSheetUrlInput = it },
                            label = { Text("Google Sheet CSV URL") },
                            placeholder = { Text("https://docs.google.com/.../export?format=csv") },
                            maxLines = 3,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Supported columns: Email, Name, Passcode, Status, Tier",
                            color = Color(0xFF9CA3AF),
                            fontSize = 11.sp
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.updateSheetUrl(customSheetUrlInput)
                            showSheetSettingsDialog = false
                        }
                    ) {
                        Text("Save & Connect")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showSheetSettingsDialog = false }) {
                        Text("Cancel", color = Color(0xFF9CA3AF))
                    }
                },
                containerColor = Color(0xFF1F2937)
            )
        }
    }
}

@Composable
private fun DemoAccountCard(
    email: String,
    pin: String,
    title: String,
    tier: String,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF151C2C))
            .clickable(onClick = onSelect)
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "PIN: $pin • $tier",
                    color = Color(0xFF9CA3AF),
                    fontSize = 11.sp
                )
            }
            Text(
                text = "USE",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
