Tree structure management 
====

Description
----

This is a web application that allows to manage tree structure.

List of functions
----
- diplay tree strucutre
- adding, changing, moving, copying nodes and leafs
- calculates sum of nodes values and save the sum in leafs

Modes
----
Applications provieds two modes of saving:
- autosave - all modifications are immidietly save in database
- non-autosave - modifications are saved only after press Save button
 
User guid
----
Start screen:
<br>
![](https://github.com/rosol88/tree/blob/master/img/start.png)

To add new node click 'Add node' button:
<br>
![](https://github.com/rosol88/tree/blob/master/img/add.png)

To add new leaf click 'Add leaf' button:
<br>
![](https://github.com/rosol88/tree/blob/master/img/add-leaf.png)

To add node or leaf to another node just select parent node and click 'Add node' or 'Add leaf'

To change value of node or leaf just double click on it and write new value:
<br>
![](https://github.com/rosol88/tree/blob/master/img/change-node.png)

When node value is changed, values of children leafs will recalculated.
When leaf value is changed, value of first parent will recalculated.a

a