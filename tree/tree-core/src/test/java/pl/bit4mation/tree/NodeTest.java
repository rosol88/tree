package pl.bit4mation.tree;

import static org.testng.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import pl.bit4mation.tree.cfg.AppConfig;
import pl.bit4mation.tree.model.Node;
import pl.bit4mation.tree.repo.NodeRepo;

@Test
@ContextConfiguration( classes = AppConfig.class )
public class NodeTest
    extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    private NodeRepo repo;

    @Test
    public void saveNode()
    {
        Node n = new Node();
        repo.save( n );
        n = repo.findOne( n.getId() );
        assertNotNull( n, "Node not created" );
    }
}
