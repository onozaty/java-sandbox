`enwiki-500000.txt` は、<a href="http://www.cl.ecei.tohoku.ac.jp/nlp100/">言語処理100本ノック 2015</a> にて配布されている下記コーパスの先頭50万行を使用させていただきました。
> 2015年1月12日時点のWikipedia記事データベースのダンプ（英語）うち，約400語以上で構成される記事の中から，ランダムに1/10サンプリングした105,752記事のテキストをbzip2形式で圧縮したものです．このファイルはクリエイティブ・コモンズ 表示-継承 3.0 非移植のライセンスで配布されています．
* http://www.cl.ecei.tohoku.ac.jp/nlp100/data/enwiki-20150112-400-r10-105752.txt.bz2

メモリ使用状況はJava Flight Recorderにて確認しています。

キャッシュなし
```
>java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=duration=60s,filename=recording.jfr -classpath tokenize-memory-reduce.jar com.enjoyxstudy.tokenize.Tokenizer enwiki-500000.txt

Started recording 1. The result will be written to:
recording.jfr

file size: 144,837,275 byte
time: 27,422.680 ms / token size: 23,170,154
```

キャッシュあり
```
>java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=duration=60s,filename=recording-cached.jfr -classpath tokenize-memory-reduce.jar com.enjoyxstudy.tokenize.CachedTokenizer enwiki-500000.txt

Started recording 1. The result will be written to:
recording-cached.jfr

file size: 144,837,275 byte
time: 10,566.430 ms / token size: 23,170,154 / cache size: 283,745
```
