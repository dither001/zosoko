package net.bytten.zosoko.generator;

import net.bytten.zosoko.IPuzzleMap;
import net.bytten.zosoko.Tile;
import net.bytten.zosoko.util.Bounds;
import net.bytten.zosoko.util.Coords;

public class TemplateMap implements IPuzzleMap {
    
    int width, height;
    Template[][] templates;
    TemplateTransform[][] transforms;

    public TemplateMap(int width, int height) {
        this.width = width;
        this.height = height;
        
        int tplCols = (width+2) / 3,
            tplRows = (height+2) / 3;
        templates = new Template[tplCols][tplRows];
        transforms = new TemplateTransform[tplCols][tplRows];
    }
    
    boolean containsTemplate(Coords pos) {
        return pos.x >= 0 && pos.x < templates.length && pos.y >= 0 &&
                pos.y < templates[0].length;
    }
    
    Template getTemplate(Coords pos) {
        return getTemplate(pos.x, pos.y);
    }
    
    Template getTemplate(int x, int y) {
        return templates[x][y];
    }
    
    TemplateTransform getTransform(Coords pos) {
        return getTransform(pos.x, pos.y);
    }
    
    TemplateTransform getTransform (int x, int y) {
        return transforms[x][y];
    }
    
    public Bounds getBounds() {
        return new Bounds(width, height);
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    @Override
    public Tile getTile(int x, int y) {
        int tplX = x / 3,
            tplY = y / 3;
        Coords tplPos = new Coords(tplX, tplY);
        int sx = x % 3,
            sy = y % 3;
        Coords tilePos = new Coords(sx, sy);
        
        Template tpl = getTemplate(tplPos);
        TemplateTransform xfm = getTransform(tplPos);
        
        tilePos = xfm.applyTile(tilePos);
        return tpl.tiles[tilePos.x][tilePos.y];
    }

    public void put(int x, int y, Template template,
            TemplateTransform transform) {
        templates[x][y] = template;
        transforms[x][y] = transform;
    }

}
