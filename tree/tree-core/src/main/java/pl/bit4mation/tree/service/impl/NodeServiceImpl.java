package pl.bit4mation.tree.service.impl;

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

/**
 * Implementation of NodeService
 * 
 * @author Paweł Rosolak
 */
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
        return findByParentId( null );
    }

    @Override
    public List<Node> findByParentId( Long parentId )
    {
        return repo.findByParentId( parentId );
    }

    @Override
    public Node get( Long id )
    {
        Node n = repo.findOne( id );
        return n;
    }

    @Override
    public void clean()
    {
        repo.deleteAll();

    }

    @Override
    public void saveNode( Node node )
    {
        if ( node.getParent() != null && !em.contains( node.getParent() ) )
        {
            Node parent = repo.findOne( node.getParent().getId() );
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
