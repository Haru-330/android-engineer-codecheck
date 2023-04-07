# 今回終了したタスク
## 初級
### ソースコードの可読性の向上
* 命名規則
* ネスト
* インデント
* スペースや改行
* コメントの適切性
### ソースコードの安全性の向上
* 非null表明演算子
* 強制ダウンキャスト
* 不必要なlateinit修飾子
* 想定外のnullの握り潰し
### バグを修正
* レイアウトエラー
* メモリリーク
* パースエラー
* 例外の処理漏れ
* クラッシュ

### Fat Fragment の回避
* Fat Fragment の回避

# 環境
* Android Studio Electric Eel | 2022.1.1 Patch 1
* Kotlin: 1.5.31
* Java：11
* Gradle: 7.0.2
* minSdk： 23
* targetSdk： 31

# 感想・聞きたい点など

## 感想
* 今まで、きちんとしたブランチ名を切っていなかったのだと反省しました。
　今回は、Issueドリブン開発を用いたので、勉強になりました。
* アーキテクチャは、今までの開発の中で、使ったことがなく、あまりわからなかったですが、調べる中で、とても勉強になりました。

## 聞きたい点
* ブランチ運用についてで、今まで私が使ってきたのは、mainからそのまま新しくブランチを生やしてプルリクエストを出してレビューしてもらう、という形式で、GitFlowは使ったことがなかったので、きちんと使えていたか教えていただきたいです。
* KotlinよりもSwift経験のほうが多かったが、大学やハッカソンの際は、MacBookを借りていて、今回借りれなかったため、あまり経験のないKotlinになってしまったので、コードの書き方など、怪しい点が数多くあるため、改善したほうが良い点は教えていただきたいです。
* FragmentやViewModelも、今回始めて使用したため、きちんとした使い方が出来ているか不安です。

# コメントの書き方
簡易的なKDocのような感じで書きます。

@paramや@returnはわかりにくい時だけで良いです。

基本的には、クラス名+説明、という感じです。
## クラス
```
/**
 * Hello クラス.
 * @param T 型引数
 * クラス内容の説明
 */
```
## 関数
```
/**
* メソッド.
* @return 戻り値の説明
* どのような処理内容かの説明
*/
```
引用：[KDoc 書き方メモ(Kotlin のドキュメンテーションコメント)](https://qiita.com/opengl-8080/items/fe43adef48e6162e6166#%E3%83%91%E3%83%A9%E3%83%A1%E3%83%BC%E3%82%BF)

# 命名規則
今回の命名規則は、[このサイト](https://kotlinlang.org/docs/coding-conventions.html#source-code-organization)をGoogle翻訳により日本語化したものを参考にします。

## 様々なケースの例

以下は、[命名規則(キャメルケース, パスカルケース, スネークケース, ケバブケース)](https://www.wakuwakubank.com/posts/804-it-naming-convention/)から引用

* キャメルケース（別名：ローワーキャメルケース）

例：`currentUserItem`

先頭の要素語( current )は小文字で書き始めます。
先頭以外の要素語( user item )の最初を大文字で書き始めます。

* パスカルケース（別名：アッパーキャメルケース）

例：`CurrentUserItem`

要素語( current user item )の最初を大文字で書き始めます。

* スネークケース

例：`current_user_item`

アンダースコア で要素語( current user item )を連結します。

* ケバブケース

例：`current-user-item`

ハイフン で要素語( current user item )を連結します。

以下は、[命名規則まとめ](https://qiita.com/deerboy/items/f035b9044edf9a51aff7)から引用

* スクリーミングスネークケース
例：`SAMPLE_TEST_DATA`

大文字で書きます。

## 実例

### ソースファイル名：パスカルケース

例：`ProcessDeclarations.kt`

### パッケージ：常に小文字

例：`org.example.project`　`org.example.myProject`

通常、複数の単語を使用することは推奨されませんが、複数の単語を使用する必要がある場合は、単語を連結するか、キャメル ケース ( )を使用できます。

### クラス・オブジェクト：パスカルケース

```kotlin
open class DeclarationProcessor { /*...*/ }
object EmptyDeclarationProcessor : DeclarationProcessor() { /*...*/ }
```

### 関数名：キャメルケース

関数、プロパティ、およびローカル変数の名前は小文字で始まり、キャメル ケースを使用し、アンダースコアは使用しません。

```kotlin
fun processDeclarations() { /*...*/ }
var declarationCount = 1
```

例外: クラスのインスタンスを作成するために使用されるファクトリ関数は、抽象戻り型と同じ名前を持つことができます。

```kotlin
interface Foo { /*...*/ }
class FooImpl : Foo { /*...*/ }
fun Foo(): Foo { return FooImpl() }
```

### テストメソッドの名前

テスト (およびテストのみ) では、バッククォートで囲まれたスペースを含むメソッド名を使用できます。このようなメソッド名は現在、Android ランタイムでサポートされていないことに注意してください。テスト コードでは、メソッド名にアンダースコアを使用することもできます。

```kotlin
class MyTestCase {
     @Test fun `ensure everything works`() { /*...*/ }

     @Test fun ensureEverythingWorks_onAndroid() { /*...*/ }
}
```

### プロパティ名：スクリーミングスネークケース(スネークケースの大文字ver.)

定数の名前 ( でマークされたプロパティ、または非常に不変なデータを保持するカスタム関数を持たないconstトップレベル プロパティまたはオブジェクトプロパティ)

```kotlin
const val MAX_COUNT = 8
val USER_NAME_FIELD = "UserName"
```

動作または変更可能なデータを持つオブジェクトを保持するトップレベルまたはオブジェクト プロパティの名前は、キャメル ケース名を使用する必要があります。

```kotlin
val mutableCollection: MutableSet<String> = HashSet()
```

シングルトン オブジェクトへの参照を保持するプロパティの名前は、宣言と同じ命名スタイルを使用できますobject。

```kotlin
val PersonComparator: Comparator<Person> = /*...*/
```

### バッキングプロパティの名前

クラスに概念的には同じであるが、1 つがパブリック API の一部であり、もう 1 つが実装の詳細である 2 つのプロパティがある場合は、プライベート プロパティの名前のプレフィックスとしてアンダースコアを使用します。

```kotlin
class C {
    private val _elementList = mutableListOf<Element>()

    val elementList: List<Element>
         get() = _elementList
}
```

### 名前の付け方

クラスの名前は通常、そのクラスが何であるかを説明する名詞または名詞句です。

例：List、PersonReader。

メソッドの名前は通常、メソッドが何をするかを示す動詞または動詞句です：close、readPersons。

名前は、オブジェクトを変更するか新しいオブジェクトを返すかを示唆する必要があります。例えば、sortはコレクションをその場でソートしますが、sortedはソートされたコレクションのコピーを返します。

名前はエンティティの目的が明確になるようにする必要があるため、名前に意味のない単語（Manager、Wrapperなど）を使用するのは避けるべきです。

宣言名の一部として略語を使用する場合は、2文字から成る場合は大文字で記述し（IOStream）、それ以上の場合は最初の文字のみを大文字で記述します（XmlFormatter、HttpInputStream）。

# ブランチ運用規則
## 今回採用するブランチ運用
[GitFlow](https://nvie.com/posts/a-successful-git-branching-model/)

[GitFlowの日本語訳](https://qiita.com/homhom44/items/9f13c646fa2619ae63d0)

今回は、GitFlowという運用例を使います。
## 命名規則について
今回はIssueドリブン開発という開発方法を使います。
ブランチの命名規則は以下のようにします。

* feature/#Issue番号_動詞_追加する機能
* スネークケースで記述する。

開発方法については、[【メモ】Issueドリブン開発とブランチ命名規則ついて【Git】](https://qiita.com/takahirocook/items/6ac94e5dc6536bd2272c)を参考にします。
> ### Issueドリブン開発の流れ
> 以下は「Webサイトのメニューをハンバーガーメニューに変更したい」となった際の一例です。
> 1. Github上で、やりたいことを記載したIssueを立てる。(issue番号は#1と仮定)発行されたIssue番号を使い、feature/#1replace_to_hamburger_menuというブランチを作成。
> 2. 開発進める。開発完了したらdevelopブランチにマージコミット。
> 3.  この際、close #12などとコミットメッセージに記述すると、自動的にIssueが閉じられるので、覚えておきましょう。

<details>
<summary>Duplicate先の原文</summary>

# 株式会社ゆめみ Android エンジニアコードチェック課題

## 概要

本プロジェクトは株式会社ゆめみ（以下弊社）が、弊社に Android エンジニアを希望する方に出す課題のベースプロジェクトです。本課題が与えられた方は、下記の概要を詳しく読んだ上で課題を取り組んでください。

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

<img src="docs/app.gif" width="320">

### 環境

- IDE：Android Studio Arctic Fox | 2020.3.1 Patch 1
- Kotlin：1.5.31
- Java：11
- Gradle：7.0.1
- minSdk：23
- targetSdk：31

※ ライブラリの利用はオープンソースのものに限ります。

### 動作

1. 何かしらのキーワードを入力
2. GitHub API（`search/repositories`）でリポジトリを検索し、結果一覧を概要（リポジトリ名）で表示
3. 特定の結果を選択したら、該当リポジトリの詳細（リポジトリ名、オーナーアイコン、プロジェクト言語、Star 数、Watcher 数、Fork 数、Issue 数）を表示

## 課題取り組み方法

Issues を確認した上、本プロジェクトを [**Duplicate** してください](https://help.github.com/en/github/creating-cloning-and-archiving-repositories/duplicating-a-repository)（Fork しないようにしてください。必要ならプライベートリポジトリにしても大丈夫です）。今後のコミットは全てご自身のリポジトリで行ってください。

コードチェックの課題 Issue は全て [`課題`](https://github.com/yumemi-inc/android-engineer-codecheck/milestone/1) Milestone がついており、難易度に応じて Label が [`初級`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3A初級+milestone%3A課題)、[`中級`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3A中級+milestone%3A課題+) と [`ボーナス`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3Aボーナス+milestone%3A課題+) に分けられています。課題の必須／選択は下記の表とします。

|   | 初級 | 中級 | ボーナス
|--:|:--:|:--:|:--:|
| 新卒／未経験者 | 必須 | 選択 | 選択 |
| 中途／経験者 | 必須 | 必須 | 選択 |

課題 Issueをご自身のリポジトリーにコピーするGitHub Actionsをご用意しております。  
[こちらのWorkflow](./.github/workflows/copy-issues.yml)を[手動でトリガーする](https://docs.github.com/ja/actions/managing-workflow-runs/manually-running-a-workflow)ことでコピーできますのでご活用下さい。

課題が完成したら、リポジトリのアドレスを教えてください。

## 参考記事

提出された課題の評価ポイントに関しては、[こちらの記事](https://qiita.com/blendthink/items/aa70b8b3106fb4e3555f)に詳しく書かれてありますので、ぜひご覧ください。

</details>
