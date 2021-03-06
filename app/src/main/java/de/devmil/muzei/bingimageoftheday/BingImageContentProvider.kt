/*
 * Copyright 2014 Devmil Solutions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.devmil.muzei.bingimageoftheday

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.ParcelFileDescriptor

import java.io.File
import java.io.FileNotFoundException
import java.util.Calendar

import de.devmil.muzei.bingimageoftheday.cache.CacheUtils

/**
 * Created by devmil on 24.02.14.

 * This content provider provides access to the cached images
 */
class BingImageContentProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        return false
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    @Throws(FileNotFoundException::class)
    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        val fileName = uri.pathSegments[1]

        val file = File(CacheUtils.getCacheDirectory(context), fileName)
        if (!file.exists())
            throw FileNotFoundException()

        return ParcelFileDescriptor.open(file,
                ParcelFileDescriptor.MODE_READ_ONLY)
    }

    companion object {

        val PROVIDER_NAME = "de.devmil.muzei.bingimageoftheday.provider.BingImages"

        val CONTENT_URI = Uri.parse("content://"
                + PROVIDER_NAME + "/images")!!

        fun getContentUri(fileName: String, useSalt: Boolean): Uri {
            val saltValue = if (useSalt) java.lang.Long.toString(Calendar.getInstance().timeInMillis) else ""

            return Uri.parse("${CONTENT_URI.toString()}/$fileName/$saltValue")
        }
    }
}
