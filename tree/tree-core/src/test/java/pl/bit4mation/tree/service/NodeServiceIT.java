package pl.bit4mation.tree.service;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

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
    private NodeService nodeService;

    @BeforeMethod
    public void setUp()
    {
        nodeService.clean();
    }

    @Test
    public void createRootNode()
    {
        Node root = new Node();
        root.setValue( 1 );
        nodeService.saveNode( root );
        List<Node> roots = nodeService.getRootNodes();
        assertEquals( roots.size(), 1, "No root created" );
        assertEquals( roots.get( 0 ).getValue(), 1, "Invalid root value" );
    }

    @Test
    public void createChild()
    {
        Node root = new Node();
        root.setValue( 1 );
        nodeService.saveNode( root );

        Node child = new Node();
        child.setValue( 2 );
        child.setParent( root );
        nodeService.saveNode( child );

        List<Node> children = nodeService.findByParentId( root.getId() );

        assertEquals( children.size(), 1, "No children found" );
        assertEquals( children.get( 0 ).getValue(), 2, "Invalid child value" );
    }

    @Test
    public void updateNodeValue()
    {
        Node node = new Node();
        node.setValue( 1 );
        nodeService.saveNode( node );

        node.setValue( 5 );
        nodeService.saveNode( node );
        node = nodeService.get( node.getId() );
        assertEquals( node.getValue(), 5 );
    }

    @Test
    public void changeParent()
    {
        Node root1 = new Node();
        root1.setValue( 1 );
        nodeService.saveNode( root1 );

        Node root2 = new Node();
        root2.setValue( 2 );
        nodeService.saveNode( root2 );

        Node child = new Node();
        child.setValue( 3 );
        child.setParent( root1 );
        nodeService.saveNode( child );

        child = nodeService.get( child.getId() );
        assertEquals( child.getParent().getId(), root1.getId() );

        child.setParent( root2 );
        nodeService.saveNode( child );

        assertEquals( child.getParent().getId(), root2.getId() );

    }

    @Test
    public void deleteNode()
    {
        Node node = new Node();
        node.setValue( 1 );
        nodeService.saveNode( node );

        nodeService.delete( node.getId() );
        node = nodeService.get( node.getId() );
        assertNull( node, "Node was not deleted" );
    }

    @Test
    public void deleteCascade()
    {
        Node node = new Node();
        node.setValue( 1 );
        nodeService.saveNode( node );

        Node child1 = new Node();
        child1.setValue( 2 );
        child1.setParent( node );
        nodeService.saveNode( child1 );

        Node child2 = new Node();
        child2.setValue( 3 );
        child2.setParent( child1 );
        nodeService.saveNode( child2 );

        nodeService.delete( node.getId() );
        assertEquals( nodeService.getRootNodes().size(), 0, "Invalid root size" );
        assertNull( nodeService.get( node.getId() ), "Root not deleted" );
        assertNull( nodeService.get( child1.getId() ), "Child 1 not deleted" );
        assertNull( nodeService.get( child2.getId() ), "Child 2 not deleted" );
    }
}
