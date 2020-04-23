package hw03.myjunit.internal;

class SomeClassTest {
    public SomeClassTest() {
    }

    @BeforeEach
    public void beforeTest() {
        System.out.println("Some before actions");
    }

    @AfterEach
    public void afterTest() {
        System.out.println("Some after actions");
    }

    @Test
    public void successTest() {
        System.out.println("Success test");
    }

    @Test
    public void failTest() {
        System.out.println("Fail test");
        Assert.assertTrue(false);
    }
}
