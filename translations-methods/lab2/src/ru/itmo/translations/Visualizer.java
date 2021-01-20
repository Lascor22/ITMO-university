package ru.itmo.translations;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import ru.itmo.translations.parser.Tree;

import javax.swing.*;

public class Visualizer extends JFrame {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 30;

    private static void recursiveDraw(mxGraph graph, Object defaultParent, Tree tree, Object parent) {
        for (Tree child : tree.getChildren()) {
            Object childVertex = graph.insertVertex(defaultParent, null, child.getNode(), 0, 0, WIDTH, HEIGHT);
            graph.insertEdge(defaultParent, null, null, parent, childVertex);
            recursiveDraw(graph, defaultParent, child, childVertex);
        }
    }

    public static void show(Tree tree) {
        Visualizer frame = new Visualizer();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        frame.draw(tree);

        frame.setVisible(true);
    }

    private void draw(Tree tree) {
        mxGraph graph = new mxGraph();
        Object defaultParent = graph.getDefaultParent();

        Object rootVertex = graph.insertVertex(defaultParent, null, tree.getNode(), 0, 0, WIDTH, HEIGHT);
        recursiveDraw(graph, defaultParent, tree, rootVertex);

        new mxCompactTreeLayout(graph, false).execute(defaultParent);
        getContentPane().add(new mxGraphComponent(graph));
    }
}

