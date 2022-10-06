package org.chicha.ttt.about

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

/**
 * Class for storing information about a software license.
 */
@Parcelize
class License(val name: String, val abbreviation: String, val filename: String) : Parcelable, Serializable
