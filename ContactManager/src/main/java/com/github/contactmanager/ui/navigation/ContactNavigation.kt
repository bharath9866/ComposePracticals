package com.github.contactmanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.contactmanager.service.PhoneCallService
import com.github.contactmanager.ui.screens.ContactDetailScreen
import com.github.contactmanager.ui.screens.ContactEditScreen
import com.github.contactmanager.ui.screens.ContactListScreen
import com.github.contactmanager.viewmodel.ContactViewModel

sealed class Screen(val route: String) {
    object ContactList : Screen("contacts")
    object ContactDetail : Screen("contact/{contactId}") {
        fun createRoute(contactId: String) = "contact/$contactId"
    }
    object ContactAdd : Screen("contact/add")
    object ContactEdit : Screen("contact/{contactId}/edit") {
        fun createRoute(contactId: String) = "contact/$contactId/edit"
    }
}

@Composable
fun ContactNavigation(
    viewModel: ContactViewModel,
    navController: NavHostController = rememberNavController()
) {
    val phoneCallService = remember { PhoneCallService() }
    
    NavHost(
        navController = navController,
        startDestination = Screen.ContactList.route
    ) {
        composable(Screen.ContactList.route) {
            ContactListScreen(
                viewModel = viewModel,
                onContactClick = { contactId ->
                    navController.navigate(Screen.ContactDetail.createRoute(contactId))
                },
                onAddContactClick = {
                    navController.navigate(Screen.ContactAdd.route)
                }
            )
        }
        
        composable(
            route = Screen.ContactDetail.route,
            arguments = listOf(
                navArgument("contactId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getString("contactId") ?: return@composable
            
            ContactDetailScreen(
                contactId = contactId,
                viewModel = viewModel,
                phoneCallService = phoneCallService,
                onBackClick = {
                    navController.navigateUp()
                },
                onEditClick = { id ->
                    navController.navigate(Screen.ContactEdit.createRoute(id))
                },
                onDeleteClick = {
                    navController.navigateUp()
                }
            )
        }
        
        composable(Screen.ContactAdd.route) {
            ContactEditScreen(
                viewModel = viewModel,
                onSaveComplete = {
                    navController.navigateUp()
                },
                onCancelClick = {
                    navController.navigateUp()
                }
            )
        }
        
        composable(
            route = Screen.ContactEdit.route,
            arguments = listOf(
                navArgument("contactId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getString("contactId") ?: return@composable
            
            ContactEditScreen(
                contactId = contactId,
                viewModel = viewModel,
                onSaveComplete = {
                    navController.navigateUp()
                },
                onCancelClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}