package piuk.blockchain.android.ui.dashboard.announcements.rule

import android.support.annotation.VisibleForTesting
import io.reactivex.Single
import piuk.blockchain.android.R
import piuk.blockchain.android.ui.dashboard.announcements.AnnouncementCard
import piuk.blockchain.android.ui.dashboard.announcements.AnnouncementHost
import piuk.blockchain.android.ui.dashboard.announcements.AnnouncementRule
import piuk.blockchain.android.ui.dashboard.announcements.DismissRecorder
import piuk.blockchain.android.ui.dashboard.announcements.DismissRule
import piuk.blockchain.android.ui.fingerprint.FingerprintHelper

class RegisterFingerprintsAnnouncement(
    dismissRecorder: DismissRecorder,
    private val fingerprints: FingerprintHelper
) : AnnouncementRule(dismissRecorder) {

    override val dismissKey = DISMISS_KEY

    override fun shouldShow(): Single<Boolean> {
        if (dismissEntry.isDismissed) {
            return Single.just(false)
        }

        return Single.just(
            fingerprints.isHardwareDetected() &&
            !fingerprints.isFingerprintUnlockEnabled()
        )
    }

    override fun show(host: AnnouncementHost) {
        host.showAnnouncementCard(
            card = AnnouncementCard(
                name = name,
                dismissRule = DismissRule.CardPeriodic,
                dismissEntry = dismissEntry,
                titleText = R.string.register_fingerprint_card_title,
                bodyText = R.string.register_fingerprint_card_body,
                ctaText = R.string.register_fingerprint_card_cta,
                iconImage = R.drawable.ic_announce_fingerprint,
                dismissFunction = {
                    host.dismissAnnouncementCard(dismissEntry.prefsKey)
                },
                ctaFunction = {
                    host.startEnableFingerprintLogin()
                }
            )
        )
    }

    override val name = "fingerprint"

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        const val DISMISS_KEY = "EnableFingerprintAnnouncement_DISMISSED"
    }
}
