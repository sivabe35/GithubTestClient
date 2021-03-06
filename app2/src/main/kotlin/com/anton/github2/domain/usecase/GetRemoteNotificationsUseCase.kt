package com.anton.github2.domain.usecase

import com.anton.github2.data.entity.Notification
import com.anton.github2.datasource.content.notifications.NotificationLocalRepository
import com.anton.github2.datasource.content.notifications.NotificationRemoteRepository
import io.reactivex.Single

interface GetRemoteNotificationsUseCase {
    fun run(page: Int): Single<List<Notification>>
}

class GetRemoteNotificationsUseCaseImpl(
    private val notificationRemoteRepository: NotificationRemoteRepository,
    private val notificationsLocalRepository: NotificationLocalRepository
) : GetRemoteNotificationsUseCase {

    override fun run(page: Int): Single<List<Notification>> {
        return notificationRemoteRepository.getNotifications(page)
            .doOnSuccess {
                if (page == 0) {
                    notificationsLocalRepository.saveNotifications(it)
                }
            }
    }

}

