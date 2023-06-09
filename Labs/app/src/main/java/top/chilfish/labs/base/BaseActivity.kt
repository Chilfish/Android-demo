package top.chilfish.labs.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import top.chilfish.labs.R

open class BaseActivity : AppCompatActivity(), ImageLoaderFactory {
    fun replaceFragment(fragment: Fragment, id: Int) {
        supportFragmentManager.beginTransaction()
            .replace(id, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .crossfade(true)
            .build()
    }
}