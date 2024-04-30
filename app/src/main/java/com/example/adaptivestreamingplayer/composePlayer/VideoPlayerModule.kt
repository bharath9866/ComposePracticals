package com.example.adaptivestreamingplayer.composePlayer

import android.app.Application
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(ViewModelComponent::class)
object VideoPlayerModule {

    @OptIn(UnstableApi::class)
    @Provides
    @ViewModelScoped
    fun provideVideoPlayer(app: Application): Player {
        val renderersFactory = DefaultRenderersFactory(app)
            .forceEnableMediaCodecAsynchronousQueueing()
        return ExoPlayer.Builder(app, renderersFactory)
            .build()
    }

    @Provides
    @Named("playerView")
    @ViewModelScoped
    fun provideVideoPlayerView(app: Application): PlayerView {
        return PlayerView(app)
    }
}