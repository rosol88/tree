package pl.bit4mation.tree.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 * Entity for Nodes and leaf
 * 
 * @author Paweł Rosolak
 */
@Entity
@SequenceGenerator( name = "id_gen", sequenceName = "node_seq" )
public class Node
{

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO, generator = "id_gen" )
    private Long id;

    private int value;

    private boolean leaf;

    @OneToMany( mappedBy = "parent" )
    private Set<Node> children;

    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn( name = "parent" )
    private Node parent;

    @Override
    public String toString()
    {
        return "Node [id=" + id + ", value=" + value + ", leaf=" + leaf + ", parent=" + parent + "]";
    }

    /**
     * @return the id from database
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId( Long id )
    {
        this.id = id;
    }

    /**
     * @return the value of node
     */
    public int getValue()
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue( int value )
    {
        this.value = value;
    }

    /**
     * @return true if element is leaf
     */
    public boolean isLeaf()
    {
        return leaf;
    }

    /**
     * @param leaf the leaf to set
     */
    public void setLeaf( boolean leaf )
    {
        this.leaf = leaf;
    }

    /**
     * @return the children
     */
    public Set<Node> getChildren()
    {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren( Set<Node> children )
    {
        this.children = children;
    }

    /**
     * @return the parent
     */
    public Node getParent()
    {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent( Node parent )
    {
        this.parent = parent;
    }

}
