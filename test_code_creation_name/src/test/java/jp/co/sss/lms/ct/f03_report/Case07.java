package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// 1. トップページへアクセス
		goTo("http://localhost:8080/lms");

		// 2. ログイン画面であることの検証
		assertEquals("ログイン | LMS", webDriver.getTitle());

		// 3. エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// 1. ログインIDとパスワードを入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA04");
		webDriver.findElement(By.id("password")).sendKeys("Nagomi0722");

		// 2. ログインボタンを押下
		webDriver.findElement(By.xpath("//input[@value='ログイン']")).click();

		// 3. ログイン成功の検証
		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("コース詳細"));

		// 4. エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 1. まずアコーディオンを「すべて開く」ボタンで開く
		webDriver.findElement(By.xpath("//*[contains(@value, 'すべて開く') or contains(text(), 'すべて開く')]")).click();

		// 2. 開いた中にある「詳細」ボタンを押下
		webDriver.findElement(By.xpath("//input[@value='詳細']")).click();

		// 3. 画面遷移の検証
		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("本日のレポート"));

		// 4. エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 1. 「日報【デモ】を提出する」ボタンを押下
		webDriver.findElement(By.xpath("//*[contains(@value, '日報') or contains(text(), '日報')]")).click();

		// 2. 画面遷移の検証
		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("日報【デモ】"));

		// 3. エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// 1. レポート記入欄（textarea）に入力
		webDriver.findElement(By.tagName("textarea")).clear();
		webDriver.findElement(By.tagName("textarea")).sendKeys("ソフトウェアについての理解が深まった。");

		// 2. 「提出する」ボタンを押下
		webDriver.findElement(By.xpath("//input[@value='提出する'] | //button[text()='提出する']")).click();

		// 3. ボタン自体を特定し、value属性またはテキストに「確認する」または「提出済み」が含まれているか検証
		String buttonValue = webDriver.findElement(By.xpath(
				"//input[contains(@value, '確認')] | //button[contains(text(), '確認')] | //input[contains(@value, '提出済み')] | //button[contains(text(), '提出済み')]"))
				.getAttribute("value");

		// value属性で取得できない場合（buttonタグの場合など）は getText() も参照
		if (buttonValue == null || buttonValue.isEmpty()) {
			buttonValue = webDriver
					.findElement(By.xpath("//button[contains(text(), '確認') or contains(text(), '提出済み')]")).getText();
		}

		assertTrue(buttonValue.contains("確認") || buttonValue.contains("提出済み"));

		// 4. エビデンス取得
		getEvidence(new Object() {
		});
	}

}
