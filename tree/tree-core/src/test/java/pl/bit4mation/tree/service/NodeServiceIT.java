package pl.bit4mation.tree.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;

import pl.bit4mation.tree.cfg.AppConfig;

@ContextConfiguration( classes = AppConfig.class )
public class NodeServiceIT
    extends AbstractTestNGSpringContextTests
{

    @Autowired
    private NodeService ns;

    @BeforeMethod
    public void setUp()
    {
        ns.clean();
    }

}
