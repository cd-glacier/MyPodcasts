package cdglacier.mypodcasts.data.episode.impl

import cdglacier.mypodcasts.data.episode.EpisodeRepository
import cdglacier.mypodcasts.model.Channel
import cdglacier.mypodcasts.model.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

val fakeRebuildEpisodes = listOf(
    Episode(
        title = "309: Museum of Unicode (kohsuke)",
        description = """<![CDATA[ <p>Kohsuke Kawaguchi さんをゲストに迎えて、起業、パンデミック、エンジニアリング、ナラティブなどについて話しました。</p>
<h3>Show Notes</h3><ul>
<li><a href="https://www.launchableinc.com/">Launchable</a></li>
<li><a href="https://kohsuke.org/2021/05/22/bike-trip-across-america-done/">Bike trip across America, DONE! - Kohsuke Kawaguchi</a></li>
<li><a href="https://www.zwift.com/">Zwift</a></li>
<li><a href="https://nullpo-head.hateblo.jp/entry/2015/03/24/205419">CPU実験で自作CPUにUNIXライクOS (xv6) を移植した話</a></li>
<li><a href="https://nuc.hatenadiary.org/entry/2021/03/31">Twitter で医師を拾ってきて Google のソフトウェアエンジニアにするだけの簡単なお仕事</a></li>
<li><a href="https://ja.kohsuke.org/software/%e5%84%aa%e7%a7%80%e3%81%95%e3%81%ab%e3%81%a4%e3%81%84%e3%81%a6/">優秀さについて</a></li>
<li><a href="https://www.thisamericanlife.org/">This American Life</a></li>
<li><a href="https://yushakobo.jp/">遊舎工房</a></li>
<li><a href="https://vishnuravi.medium.com/how-do-verifiable-covid-19-vaccination-records-with-smart-health-cards-work-df099370b27a">How do Verifiable Vaccination Records with SMART Health Cards Work?</a></li>
<li><a href="https://www.kalzumeus.com/2010/06/17/falsehoods-programmers-believe-about-names/">Falsehoods Programmers Believe About Names</a></li>
<li><a href="https://en.wikipedia.org/wiki/Han_unification">Han unification</a></li>
<li><a href="https://ja.kohsuke.org/software/mizuho-outage/">みずほ銀行システム障害に学ぶ</a></li>
<li><a href="https://www.mizuho-fg.co.jp/release/20210615release_jp.html">みずほFG：システム障害特別調査委員会の調査報告書の受領について</a></li>
<li><a href="https://www.bbc.co.uk/programmes/w13xttx2/episodes/downloads">BBC World Service - 13 Minutes to the Moons</a></li>
<li><a href="https://www.wnycstudios.org/podcasts/radiolab">Radiolab: Podcasts</a></li>
<li><a href="https://japanese.stackexchange.com/">Japanese Language Stack Exchange</a></li>
</ul>
 ]]>""",
        publishedAt = "Sun, 27 Jun 2021 23:00:00 -0700",
        mediaUrl = "https://cache.rebuild.fm/podcast-ep309.mp3",
        lengthSecond = 62570039,
        episodeWebSiteUrl = "https://rebuild.fm/309/",
        channel = Episode.Channel(
            domain = "rebuild.fm",
            name = "Rebuild",
            imageUrl = "https://cdn.rebuild.fm/images/icon240.png",
            author = "Tatsuhiko Miyagawa"
        )
    )
)

val fakeTalkingKotlinEpisodes = listOf(
    Episode(
        title = "Kotlin in Education (Talking Kotlin #101)",
        description = "In this episode, we’ll sit down with Ksenia Shneyveys, the Kotlin Manager for Education and University Relations at JetBrains, and talk to her about the current state and future of Kotlin in academia. Kseniya will tell us about the recent increase in institutions and educators teaching Kotlin, including adoption by Stanford, Cambridge, Imperial College London, University of Chicago, and many other prestigious institutions.",
        publishedAt = "Sat, 17 Jul 2021 13:45:00 +0000",
        mediaUrl = "https://feeds.soundcloud.com/stream/1088610637-user-38099918-kotlin-in-education-talking-kotlin-101.mp3",
        lengthSecond = 30439966,
        episodeWebSiteUrl = "https://soundcloud.com/user-38099918/kotlin-in-education-talking-kotlin-101",
        channel = Episode.Channel(
            domain = "talkingkotlin.com",
            name = "Talking Kotlin",
            imageUrl = "https://i1.sndcdn.com/avatars-000289370353-di6ese-original.jpg",
            author = null
        )
    ),
)

class FakeEpisodeRepositoryImpl : EpisodeRepository {
    override suspend fun getEpisodes(channel: Channel): Result<List<Episode>> =
        withContext(Dispatchers.IO) {
            delay(500)
            if (channel.domain == "rebuild.fm") {
                Result.success(fakeRebuildEpisodes)
            } else if (channel.domain == "talkingkotlin.com") {
                Result.success(fakeTalkingKotlinEpisodes)
            } else {
                Result.failure(Exception("not found"))
            }
        }

    override suspend fun storeNewEpisodes(channel: Channel): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubscribedEpisodes(): Result<List<Episode>> = Result.success(
        fakeRebuildEpisodes
    )

    override suspend fun getEpisode(channel: Channel, title: String): Result<Episode> =
        withContext(Dispatchers.IO) {
            if (channel.domain == "rebuild.fm" && title == "309: Museum of Unicode (kohsuke)") {
                Result.success(fakeRebuildEpisodes.first())
            } else if (channel.domain == "talkingkotlin.com" && title == "Kotlin in Education (Talking Kotlin #101)") {
                Result.success(fakeTalkingKotlinEpisodes.first())
            } else {
                Result.failure(Exception("episode not found"))
            }
        }
}