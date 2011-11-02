package net.homeip.jtjang.MileageRun;

import java.io.*;

public class KayakMockFeed {

	public static final String ENCODING = "US-ASCII";

	public static InputStream rawStream() {
		try {
			byte[] bytes = rawText().getBytes(ENCODING);
			return new ByteArrayInputStream(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String rawText() {
		return
        "<?xml version=\"1.0\"?>" + 
        "<rss version=\"2.0\"  xmlns:kyk=\"http://www.kayak.com/h/rss/buzzextension\">" + 
        "   <channel>" + 
        "      <title>Kayak Buzz - NYC to &quot;Top 25&quot;  Dec 2011 </title>" + 
        "      <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fcode%3Dnyc%26tm%3D201112</link>" + 
        "      <description>Kayak Buzz Alerts</description>" + 
        "      <language>en-us</language>" + 
        "      <pubDate>Mon, 05 Sep 2011 20:00:00 EDT</pubDate>" + 
        "      <lastBuildDate>Mon, 05 Sep 2011 20:00:00 EDT</lastBuildDate>" + 
        "      <docs>http://www.kayak.com/h/labs/rss</docs>" + 
        "      <managingEditor>webmaster@kayak.com</managingEditor>" + 
        "      <webMaster>webmaster@kayak.com</webMaster>" + 
        "      <item>" + 
        "         <title>nyc to CHI $117 Dec 3 - 6 on American Airlines found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DCHI%26desthint1%3DCHI</link>" + 
        "         <description>nyc to CHI $117 Dec 3 - 6 on American Airlines found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DCHI%26desthint1%3DCHI</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>CHI</kyk:destCode>" + 
        "            <kyk:destName>All airports</kyk:destName>" + 
        "            <kyk:destLocation>Chicago, IL</kyk:destLocation>" + 
        "            <kyk:airline>American Airlines</kyk:airline>" + 
        "            <kyk:price>117</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/3/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/6/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to ORD $117 Dec 3 - 6 on American Airlines found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DORD%26desthint1%3DORD</link>" + 
        "         <description>nyc to ORD $117 Dec 3 - 6 on American Airlines found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DORD%26desthint1%3DORD</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>ORD</kyk:destCode>" + 
        "            <kyk:destName>O&apos;Hare International</kyk:destName>" + 
        "            <kyk:destLocation>Chicago, IL</kyk:destLocation>" + 
        "            <kyk:airline>American Airlines</kyk:airline>" + 
        "            <kyk:price>117</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/3/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/6/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to MSP $187 Dec 1 - 4 on US Airways found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DMSP%26desthint1%3DMSP</link>" + 
        "         <description>nyc to MSP $187 Dec 1 - 4 on US Airways found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DMSP%26desthint1%3DMSP</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>MSP</kyk:destCode>" + 
        "            <kyk:destName>Minneapolis St Paul</kyk:destName>" + 
        "            <kyk:destLocation>Minneapolis, MN</kyk:destLocation>" + 
        "            <kyk:airline>US Airways</kyk:airline>" + 
        "            <kyk:price>187</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/1/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/4/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to FLL $187 Dec 6 - 10 on Spirit Airlines found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DFLL%26desthint1%3DFLL</link>" + 
        "         <description>nyc to FLL $187 Dec 6 - 10 on Spirit Airlines found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DFLL%26desthint1%3DFLL</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>FLL</kyk:destCode>" + 
        "            <kyk:destName>Fort Lauderdale</kyk:destName>" + 
        "            <kyk:destLocation>Fort Lauderdale, FL</kyk:destLocation>" + 
        "            <kyk:airline>Spirit Airlines</kyk:airline>" + 
        "            <kyk:price>187</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/6/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/10/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to MCO $199 Dec 7 - 12 on Continental found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DMCO%26desthint1%3DMCO</link>" + 
        "         <description>nyc to MCO $199 Dec 7 - 12 on Continental found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DMCO%26desthint1%3DMCO</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>MCO</kyk:destCode>" + 
        "            <kyk:destName>Orlando</kyk:destName>" + 
        "            <kyk:destLocation>Orlando, FL</kyk:destLocation>" + 
        "            <kyk:airline>Continental</kyk:airline>" + 
        "            <kyk:price>199</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/7/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/12/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to MIA $199 Dec 1 - 4 on Delta found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DMIA%26desthint1%3DMIA</link>" + 
        "         <description>nyc to MIA $199 Dec 1 - 4 on Delta found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DMIA%26desthint1%3DMIA</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>MIA</kyk:destCode>" + 
        "            <kyk:destName>Miami</kyk:destName>" + 
        "            <kyk:destLocation>Miami, FL</kyk:destLocation>" + 
        "            <kyk:airline>Delta</kyk:airline>" + 
        "            <kyk:price>199</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/1/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/4/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to DTW $203 Dec 25 - 25 on Multiple Airlines found 1 day ago</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DDTW%26desthint1%3DDTW</link>" + 
        "         <description>nyc to DTW $203 Dec 25 - 25 on Multiple Airlines found 1 day ago</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DDTW%26desthint1%3DDTW</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>DTW</kyk:destCode>" + 
        "            <kyk:destName>Detroit Metro </kyk:destName>" + 
        "            <kyk:destLocation>Detroit, MI</kyk:destLocation>" + 
        "            <kyk:airline>Multiple Airlines</kyk:airline>" + 
        "            <kyk:price>203</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/25/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/25/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to TPA $209 Dec 1 - 5 on United found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DTPA%26desthint1%3DTPA</link>" + 
        "         <description>nyc to TPA $209 Dec 1 - 5 on United found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DTPA%26desthint1%3DTPA</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>TPA</kyk:destCode>" + 
        "            <kyk:destName>Tampa</kyk:destName>" + 
        "            <kyk:destLocation>Tampa, FL</kyk:destLocation>" + 
        "            <kyk:airline>United</kyk:airline>" + 
        "            <kyk:price>209</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/1/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/5/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to DFW $211 Dec 7 - 13 on Spirit Airlines found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DDFW%26desthint1%3DDFW</link>" + 
        "         <description>nyc to DFW $211 Dec 7 - 13 on Spirit Airlines found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DDFW%26desthint1%3DDFW</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>DFW</kyk:destCode>" + 
        "            <kyk:destName>Dallas/Ft Worth Intl</kyk:destName>" + 
        "            <kyk:destLocation>Dallas/Fort Worth, TX</kyk:destLocation>" + 
        "            <kyk:airline>Spirit Airlines</kyk:airline>" + 
        "            <kyk:price>211</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/7/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/13/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to ATL $212 Dec 3 - 6 on Multiple Airlines found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DATL%26desthint1%3DATL</link>" + 
        "         <description>nyc to ATL $212 Dec 3 - 6 on Multiple Airlines found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DATL%26desthint1%3DATL</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>ATL</kyk:destCode>" + 
        "            <kyk:destName>Hartsfield-Jackson </kyk:destName>" + 
        "            <kyk:destLocation>Atlanta, GA</kyk:destLocation>" + 
        "            <kyk:airline>Multiple Airlines</kyk:airline>" + 
        "            <kyk:price>212</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/3/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/6/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to CHS $228 Dec 19 - Jan 23 on US Airways found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DCHS%26desthint1%3DCHS</link>" + 
        "         <description>nyc to CHS $228 Dec 19 - Jan 23 on US Airways found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DCHS%26desthint1%3DCHS</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>CHS</kyk:destCode>" + 
        "            <kyk:destName>AFB-Municipal</kyk:destName>" + 
        "            <kyk:destLocation>Charleston, SC</kyk:destLocation>" + 
        "            <kyk:airline>US Airways</kyk:airline>" + 
        "            <kyk:price>228</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/19/11</kyk:departDate>" + 
        "            <kyk:returnDate>01/23/12</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to MSY $258 Dec 1 - 4 on US Airways found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DMSY%26desthint1%3DMSY</link>" + 
        "         <description>nyc to MSY $258 Dec 1 - 4 on US Airways found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DMSY%26desthint1%3DMSY</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>MSY</kyk:destCode>" + 
        "            <kyk:destName>Louis Armstrong </kyk:destName>" + 
        "            <kyk:destLocation>New Orleans, LA</kyk:destLocation>" + 
        "            <kyk:airline>US Airways</kyk:airline>" + 
        "            <kyk:price>258</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/1/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/4/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to DEN $266 Dec 7 - 10 on Multiple Airlines found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DDEN%26desthint1%3DDEN</link>" + 
        "         <description>nyc to DEN $266 Dec 7 - 10 on Multiple Airlines found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DDEN%26desthint1%3DDEN</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>DEN</kyk:destCode>" + 
        "            <kyk:destName>Denver International</kyk:destName>" + 
        "            <kyk:destLocation>Denver, CO</kyk:destLocation>" + 
        "            <kyk:airline>Multiple Airlines</kyk:airline>" + 
        "            <kyk:price>266</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/7/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/10/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to SJU $271 Dec 2 - 5 on Multiple Airlines found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DSJU%26desthint1%3DSJU</link>" + 
        "         <description>nyc to SJU $271 Dec 2 - 5 on Multiple Airlines found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DSJU%26desthint1%3DSJU</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>SJU</kyk:destCode>" + 
        "            <kyk:destName>Luis Munoz Marin </kyk:destName>" + 
        "            <kyk:destLocation>San Juan, Puerto Rico</kyk:destLocation>" + 
        "            <kyk:airline>Multiple Airlines</kyk:airline>" + 
        "            <kyk:price>271</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/2/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/5/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to AUS $272 Dec 1 - 6 on Multiple Airlines found 1 day ago</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DAUS%26desthint1%3DAUS</link>" + 
        "         <description>nyc to AUS $272 Dec 1 - 6 on Multiple Airlines found 1 day ago</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DAUS%26desthint1%3DAUS</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>AUS</kyk:destCode>" + 
        "            <kyk:destName>Austin Bergstrom</kyk:destName>" + 
        "            <kyk:destLocation>Austin, TX</kyk:destLocation>" + 
        "            <kyk:airline>Multiple Airlines</kyk:airline>" + 
        "            <kyk:price>272</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/1/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/6/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to LAX $306 Dec 9 - 18 on Delta found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DLAX%26desthint1%3DLAX</link>" + 
        "         <description>nyc to LAX $306 Dec 9 - 18 on Delta found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DLAX%26desthint1%3DLAX</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>LAX</kyk:destCode>" + 
        "            <kyk:destName>Los Angeles</kyk:destName>" + 
        "            <kyk:destLocation>Los Angeles, CA</kyk:destLocation>" + 
        "            <kyk:airline>Delta</kyk:airline>" + 
        "            <kyk:price>306</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/9/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/18/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to LAS $312 Dec 5 - 19 on Delta found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DLAS%26desthint1%3DLAS</link>" + 
        "         <description>nyc to LAS $312 Dec 5 - 19 on Delta found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DLAS%26desthint1%3DLAS</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>LAS</kyk:destCode>" + 
        "            <kyk:destName>McCarran</kyk:destName>" + 
        "            <kyk:destLocation>Las Vegas, NV</kyk:destLocation>" + 
        "            <kyk:airline>Delta</kyk:airline>" + 
        "            <kyk:price>312</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/5/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/19/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to SAN $331 Dec 6 - 13 on US Airways found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DSAN%26desthint1%3DSAN</link>" + 
        "         <description>nyc to SAN $331 Dec 6 - 13 on US Airways found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DSAN%26desthint1%3DSAN</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>SAN</kyk:destCode>" + 
        "            <kyk:destName>San Diego</kyk:destName>" + 
        "            <kyk:destLocation>San Diego, CA</kyk:destLocation>" + 
        "            <kyk:airline>US Airways</kyk:airline>" + 
        "            <kyk:price>331</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/6/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/13/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to SFO $335 Dec 7 - 11 on American Airlines found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DSFO%26desthint1%3DSFO</link>" + 
        "         <description>nyc to SFO $335 Dec 7 - 11 on American Airlines found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DSFO%26desthint1%3DSFO</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>SFO</kyk:destCode>" + 
        "            <kyk:destName>San Francisco</kyk:destName>" + 
        "            <kyk:destLocation>San Francisco, CA</kyk:destLocation>" + 
        "            <kyk:airline>American Airlines</kyk:airline>" + 
        "            <kyk:price>335</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/7/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/11/11</kyk:returnDate>" + 
        "      </item>" + 
        "      <item>" + 
        "         <title>nyc to CUN $339 Dec 1 - 5 on United found today</title>" + 
        "         <link>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DCUN%26desthint1%3DCUN</link>" + 
        "         <description>nyc to CUN $339 Dec 1 - 5 on United found today</description>" + 
        "         <pubDate>Tue, 06 Sep 2011 17:54:50 EDT</pubDate>" + 
        "         <guid>http://www.kayak.com/in?ai=&amp;p=&amp;url=%2Fh%2Fbuzz%2Fview%3Fac%3D%26action%3Dcreate%26airport%3Dnyc%26code%3Dnyc%26dt%3DA%26t%3D%26mc%3DUSD%26tm%3D201112%26dest1%3DCUN%26desthint1%3DCUN</guid>" + 
        "            <kyk:originCode>NYC</kyk:originCode>" + 
        "            <kyk:originName>All airports</kyk:originName>" + 
        "            <kyk:originLocation>New York, NY</kyk:originLocation>" + 
        "            <kyk:destCode>CUN</kyk:destCode>" + 
        "            <kyk:destName>Cancun</kyk:destName>" + 
        "            <kyk:destLocation>Cancun, QR, Mexico</kyk:destLocation>" + 
        "            <kyk:airline>United</kyk:airline>" + 
        "            <kyk:price>339</kyk:price>" + 
        "            <kyk:currency>USD</kyk:currency>" + 
        "            <kyk:departDate>12/1/11</kyk:departDate>" + 
        "            <kyk:returnDate>12/5/11</kyk:returnDate>" + 
        "      </item>" + 
        "   </channel>" + 
        "</rss>";
	}
}
