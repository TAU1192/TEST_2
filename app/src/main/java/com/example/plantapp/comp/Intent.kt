package com.example.plantapp.comp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plantapp.Create
import com.example.plantapp.Home
import com.example.plantapp.LogIn
import com.example.plantapp.NavigationBarExample


@Composable
fun AppView(){
    val navController = rememberNavController()
    NavHost(
        navController=navController,
        startDestination = Screens.LOGIN.name) {  //初期値の設定のようなもの
        composable(Screens.LOGIN.name) {
            LogIn(
                toHome = { navController.navigate(Screens.HOME.name) },
                toCreate = { navController.navigate(Screens.CREATE.name) }  //それぞれの引数に対応するように設定。*ラムダ式が複数の場合はLogInのように設定しなければならない
            )
        }
        composable(Screens.HOME.name){
            NavigationBarExample()
        }
        composable(Screens.CREATE.name){
            Create{
                navController.navigate(Screens.LOGIN.name)
            }
        }
    }
}


enum class Screens{
    HOME,
    LOGIN,
    CREATE,

}

//intentのコード

