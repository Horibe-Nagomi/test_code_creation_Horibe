package jp.co.sss.lms.ct.f06_login2;

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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能②
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン パスワード変更 入力チェック")
public class Case16 {

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
		goTo("http://localhost:8080/lms/");
		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("ログイン") || webDriver.getTitle().contains("ログイン"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAB05");
		webDriver.findElement(By.id("password")).sendKeys("StudentAB05");

		webDriver.findElement(By.xpath("//input[@type='submit'] | //button[@type='submit']")).click();

		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("利用規約"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {
		webDriver.findElement(By.xpath("//input[@type='checkbox']")).click();
		webDriver.findElement(By.xpath("//input[@value='次へ'] | //button[contains(text(),'次へ')]")).click();

		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("パスワード変更"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {
		// 未入力のまま「変更」ボタンを押下
		webDriver.findElement(By.xpath("//input[@value='変更'] | //button[contains(text(),'変更')]")).click();

		// エラー文言の検証
		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("必須") || actualText.contains("入力してください"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {
		// 画面上のパスワード入力欄（上から1つ目、2つ目、3つ目）をシンプルに取得
		WebElement current = webDriver.findElement(By.xpath("(//input[@type='password'])[1]"));
		WebElement newPass = webDriver.findElement(By.xpath("(//input[@type='password'])[2]"));
		WebElement confirm = webDriver.findElement(By.xpath("(//input[@type='password'])[3]"));

		current.sendKeys("StudentAB05");
		newPass.sendKeys("AAAAAAAAAAAAAAAAAAAA1"); // 21文字
		confirm.sendKeys("AAAAAAAAAAAAAAAAAAAA1");

		webDriver.findElement(By.xpath("//input[@value='変更'] | //button[contains(text(),'変更')]")).click();

		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("20") || actualText.contains("文字") || actualText.contains("桁")
				|| actualText.contains("最大"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {
		WebElement current = webDriver.findElement(By.xpath("(//input[@type='password'])[1]"));
		WebElement newPass = webDriver.findElement(By.xpath("(//input[@type='password'])[2]"));
		WebElement confirm = webDriver.findElement(By.xpath("(//input[@type='password'])[3]"));

		current.sendKeys("StudentAB05");
		newPass.sendKeys("password"); // 数字・大文字なし
		confirm.sendKeys("password");

		webDriver.findElement(By.xpath("//input[@value='変更'] | //button[contains(text(),'変更')]")).click();

		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("半角") || actualText.contains("英") || actualText.contains("数字")
				|| actualText.contains("形式") || actualText.contains("ポリシー"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {
		WebElement current = webDriver.findElement(By.xpath("(//input[@type='password'])[1]"));
		WebElement newPass = webDriver.findElement(By.xpath("(//input[@type='password'])[2]"));
		WebElement confirm = webDriver.findElement(By.xpath("(//input[@type='password'])[3]"));

		current.sendKeys("StudentAB05");
		newPass.sendKeys("Nagomi0715");
		confirm.sendKeys("Different123");

		webDriver.findElement(By.xpath("//input[@value='変更'] | //button[contains(text(),'変更')]")).click();

		String actualText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(actualText.contains("一致") || actualText.contains("相違") || actualText.contains("同じ")
				|| actualText.contains("エラー"));

		getEvidence(new Object() {
		});
	}
}