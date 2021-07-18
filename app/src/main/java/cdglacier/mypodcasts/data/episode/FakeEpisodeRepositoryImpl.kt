package cdglacier.mypodcasts.data.episode

import cdglacier.mypodcasts.model.Channel
import cdglacier.mypodcasts.model.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

val fakeEpisodes = listOf(
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
            name = "Rebuild",
            imageUrl = "https://cdn.rebuild.fm/images/icon240.png"
        )
    )
)

class FakeEpisodeRepositoryImpl : EpisodeRepository {
    override suspend fun getEpisodes(channel: Channel): Result<List<Episode>> =
        withContext(Dispatchers.IO) {
            delay(1000)
            if (channel.webSiteUrl == "https://rebuild.fm") {
                Result.success(fakeEpisodes)
            } else {
                Result.failure(Exception("not found"))
            }
        }
}