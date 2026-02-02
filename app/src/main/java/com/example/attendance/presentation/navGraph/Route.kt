package com.example.attendance.presentation.navGraph

import com.google.android.gms.common.api.Batch
import kotlinx.serialization.Serializable

sealed interface Route {
    val routeName: String
    @Serializable
    object LoginScreen : Route {
        override val routeName : String
            get() = "loginScreen"
    }
    @Serializable
    object HomeScreen : Route {
        override val routeName: String
            get() = "homeScreen"
    }

    @Serializable
    object BatchListScreen : Route {
        override val routeName: String
            get() = "batchListScreen"
    }

    @Serializable
    object CandidateListScreen : Route {
        override val routeName: String
            get() = "candidateListScreen"

        fun withBatchId(batchId: Long): String{
            return "$routeName/$batchId"
        }
    }

    @Serializable
    object AttendanceScreen : Route {
        override val routeName: String
            get() = "attendanceScreen"

        fun withArgs(userType: String,userId: String): String{
            return "$routeName/$userType/$userId"
        }
    }
}