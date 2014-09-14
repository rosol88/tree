package pl.bit4mation.tree.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.bit4mation.tree.model.Node;

/**
 * Repositiory for Node entity. Responsible for executing database operations on Node entity
 * 
 * @author Paweł Rosolak
 */
public interface NodeRepo
    extends CrudRepository<Node, Long>
{

    /**
     * Search childern of node
     * 
     * @param parentId Parent node id
     * @return list of children
     */
    List<Node> findByParentId( Long parentId );
}
