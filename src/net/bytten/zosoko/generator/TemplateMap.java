package net.bytten.zosoko.generator;

import net.bytten.zosoko.IPuzzleMap;
import net.bytten.zosoko.Tile;
import net.bytten.gameutil.Rect2I;
import net.bytten.gameutil.Vec2I;

public class TemplateMap implements IPuzzleMap {
    
    boolean bounded;
    int width, height;
    Template[][] templates;
    TemplateTransform[][] transforms;

    public TemplateMap(boolean bounded, int width, int height) {
        this.bounded = bounded;
        this.width = width;
        this.height = height;
        
        int tplCols = (width+2) / 3,
            tplRows = (height+2) / 3;
        templates = new Template[tplCols][tplRows];
        transforms = new TemplateTransform[tplCols][tplRows];
    }
    
    boolean containsTemplate(Vec2I pos) {
        return pos.x >= 0 && pos.x < templates.length && pos.y >= 0 &&
                pos.y < templates[0].length;
    }
    
    Template getTemplate(Vec2I pos) {
        return getTemplate(pos.x, pos.y);
    }
    
    Template getTemplate(int x, int y) {
        return templates[x][y];
    }
    
    TemplateTransform getTransform(Vec2I pos) {
        return getTransform(pos.x, pos.y);
    }
    
    TemplateTransform getTransform (int x, int y) {
        return transforms[x][y];
    }
    
    public Rect2I getBounds() {
        return new Rect2I(0, 0, width, height);
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
        Vec2I tplPos = new Vec2I(tplX, tplY);
        int sx = x % 3,
            sy = y % 3;
        Vec2I tilePos = new Vec2I(sx, sy);
        
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

    @Override
    public boolean isPlayerBounded() {
        return bounded;
    }

}
