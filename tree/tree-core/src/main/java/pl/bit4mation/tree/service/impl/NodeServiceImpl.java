package pl.bit4mation.tree.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.bit4mation.tree.model.Node;
import pl.bit4mation.tree.repo.NodeRepo;
import pl.bit4mation.tree.service.NodeService;

@Service
@Transactional
public class NodeServiceImpl
    implements NodeService
{
    private static Logger log = LoggerFactory.getLogger( NodeServiceImpl.class );

    @Autowired
    private NodeRepo repo;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Node> getRootNodes()
    {
        return repo.findByParent( null );
    }

    @Override
    public Node createNode( Node node )
    {
        return repo.save( node );
    }

    @Override
    public Node get( Long id )
    {
        Node n = repo.findOne( id );
        n.setSum( sumParent( n.getParent() ) );
        loadChildren( n );

        return n;
    }

    private int sumParent( Node n )
    {
        int sum = 0;
        if ( n == null )
        {
            return sum;
        }
        else if ( n.getParent() != null )
        {
            sum += sumParent( n.getParent() );
        }
        sum += n.getValue();
        return sum;
    }

    private void loadChildren( Node n )
    {
        for ( Node child : n.getChildren() )
        {
            child.setSum( child.getSum() + n.getSum() );
            loadChildren( child );
        }
    }

    @Override
    public void clean()
    {
        repo.deleteAll();

    }

    @Override
    public void save( Collection<Node> nodes, Node parent )
    {
        if ( nodes == null )
            return;
        for ( Node node : nodes )
        {
            if ( parent != null && parent.getId() != null )
                parent = repo.findOne( parent.getId() );
            if ( node.getParent() != null && node.getParent().getId() != null )
                parent = repo.findOne( node.getParent().getId() );
            node.setParent( parent );
            log.debug( "Saving {}", node );
            repo.save( node );
            save( node.getChildren(), node );
        }
    }

    @Override
    public void save( Node node, Long parentId )
    {
        if ( parentId != null )
        {
            Node parent = repo.findOne( parentId );
            node.setParent( parent );
        }
        repo.save( node );
    }

    @Override
    public void delete( Long nodeId )
    {
        Node node = repo.findOne( nodeId );
        removeChildren( node.getChildren() );
        repo.delete( nodeId );
    }

    private void removeChildren( Set<Node> children )
    {
        for ( Node node : children )
        {
            removeChildren( node.getChildren() );
            log.debug( "Deleting: {}", node );
            repo.delete( node );
        }
    }
}
