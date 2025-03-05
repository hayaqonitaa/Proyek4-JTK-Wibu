package com.example.jtkwibu.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

class TopAnimePagingSource(
    private val apiService: JikanApiService
) : PagingSource<Int, AnimeNetworkModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeNetworkModel> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getTopAnime(page)
            Log.d("JtkWibu", "API Response on page $page: $response")
            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.e("JtkWibu", "Error loading page $page", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AnimeNetworkModel>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
