package core.framework.impl.web.route;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author neo
 */
public class PathPatternValidatorTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private PathPatternValidator validator;

    @Before
    public void createPathPatternValidator() {
        validator = new PathPatternValidator();
    }

    @Test
    public void duplicatedVariable() {
        exception.expect(Error.class);
        exception.expectMessage("duplicated");

        validator.validate("/:name/path/:name");
    }

    @Test
    public void validate() {
        validator.validate("/robot.txt");
        validator.validate("/images");

        validator.validate("/path-with-trailing-slash/");

        validator.validate("/user/:id/name");
        validator.validate("/v2/user/:id");
    }
}