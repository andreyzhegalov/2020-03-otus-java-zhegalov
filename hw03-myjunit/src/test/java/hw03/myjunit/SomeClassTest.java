package hw03.myjunit;

class SomeClassTest {
    public SomeClassTest() {
    }

    @Before
    public void beforeTest() {
        System.out.println("Some before actions");
    }

    @After
    public void afterTest() {
        System.out.println("Some after actions");
    }

    @Test
    public void successTest() {
    }

    @Test
    public void failTest() {
        Assert.fail(null);
    }
}
