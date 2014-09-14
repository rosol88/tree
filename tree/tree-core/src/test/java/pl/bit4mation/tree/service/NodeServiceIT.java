package pl.bit4mation.tree.service;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.bit4mation.tree.cfg.AppConfig;
import pl.bit4mation.tree.model.Node;

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

    @Test
    public void rootNode()
    {
        Node n = new Node();
        ns.createNode( n );
        List<Node> roots = ns.getRootNodes();
        assertEquals( roots.size(), 1 );
    }

    @Test
    public void allTree()
    {
        Node n = new Node();
        n.setValue( 1 );

        Node n2 = new Node();
        n2.setValue( 2 );
        ns.createNode( n2 );

        Node n3 = new Node();
        n3.setValue( 3 );

        n3.setParent( n );

        Node n4 = new Node();
        n4.setValue( 4 );
        n4.setParent( n3 );
        ns.createNode( n4 );

        List<Node> roots = ns.getRootNodes();
        assertEquals( roots.size(), 2 );
        n = ns.get( n.getId() );
        assertEquals( n.getChildren().size(), 1 );
        n2 = ns.get( n2.getId() );
        assertEquals( n2.getChildren().size(), 0 );

        assertEquals( ns.get( 1L ).getSum(), 0 );
        assertEquals( ns.get( 2L ).getSum(), 0 );
        assertEquals( ns.get( 3L ).getSum(), 1 );
        assertEquals( ns.get( 4L ).getSum(), 4 );

    }

    @Test
    public void addLeaf()
    {
        Node n = new Node();
        n.setValue( 1 );
        ns.createNode( n );

        Node n2 = new Node();
        n2.setValue( 2 );
        ns.save( n2, n.getId() );

        Node n3 = new Node();
        n3.setValue( 3 );
        n3.setLeaf( true );

        ns.save( n3, n2.getId() );

        List<Node> roots = ns.getRootNodes();
        assertEquals( roots.size(), 1 );
        n = ns.get( n.getId() );
        assertEquals( n.getChildren().size(), 1 );
        n2 = ns.get( n2.getId() );
        assertEquals( n2.getChildren().size(), 1 );
        assertEquals( n2.getChildren().iterator().next().getValue(), 3 );

    }

    @Test
    public void deleteNodeWithChildren()
    {
        Node n = new Node();
        n.setValue( 1 );
        ns.createNode( n );

        Node n2 = new Node();
        n2.setValue( 2 );
        ns.save( n2, n.getId() );

        ns.delete( n.getId() );
        List<Node> roots = ns.getRootNodes();
        assertEquals( roots.size(), 0 );

    }
}
