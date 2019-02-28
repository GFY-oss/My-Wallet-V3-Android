package piuk.blockchain.android.ui.dashboard.announcements

import com.blockchain.announcement.AnnouncementList
import piuk.blockchain.android.ui.dashboard.DashboardPresenter

class DashboardAnnouncements internal constructor(
    stellarModalPopupAnnouncement: StellarModalPopupAnnouncement
) {
    val announcementList = AnnouncementList<DashboardPresenter>()
        .add(stellarModalPopupAnnouncement)
}
