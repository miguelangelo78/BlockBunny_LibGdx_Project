package com.mygdx.entities;

import static com.mygdx.globals.B2DVars.PPM;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class WorldEntity extends EntityManager{
	private World world;
	
	public WorldEntity(World world){
		this.world=world;

		
	}
	
	public void createLayer(TiledMapTileLayer layer,short bits,float tileSize,String layerName){
		BodyDef bdef=new BodyDef();
		FixtureDef fdef=new FixtureDef();
		
		//go through all the cells in the layer
		for(int row=0;row<layer.getHeight();row++) for(int col=0;col<layer.getWidth();col++){
				Cell cell=layer.getCell(col, row);
				if(cell==null) continue; if(cell.getTile()==null) continue;
				//create a body + fixture from cell
				bdef.type=BodyType.StaticBody;
				bdef.position.set(new Vector2((col+.5f)*tileSize/PPM,(row+.5f)*tileSize/PPM));
					
				// set polygon for the floor
				ChainShape shape=new ChainShape();
				Vector2[]vertices=new Vector2[5];
				vertices[0]=new Vector2(-tileSize/2/PPM,-tileSize/2/PPM);
				vertices[1]=new Vector2(-tileSize/2/PPM,tileSize/2/PPM);
				vertices[2]=new Vector2(tileSize/2/PPM,tileSize/2/PPM);
				vertices[3]=new Vector2(tileSize/2/PPM,-tileSize/2/PPM);
				vertices[4]=new Vector2(-tileSize/2/PPM,-tileSize/2/PPM);
				shape.createChain(vertices);
				fdef.shape=shape;
				
				// set floor properties:
				fdef.friction=0.4f;
				fdef.restitution=.0f;
				fdef.filter.categoryBits=bits;
				fdef.filter.maskBits=-1;
				fdef.isSensor=false;
				Body body=world.createBody(bdef);
				body.createFixture(fdef).setUserData(layerName);
		
				shape.dispose();
			}
	}

	public void createLayer(MapObjects objects,short bits,float tileSize,String layerName){
		Body body;
		BodyDef bdef=new BodyDef();
		FixtureDef fdef=new FixtureDef();
		bdef.type=BodyType.StaticBody;
		fdef.friction=0.4f;
		fdef.filter.categoryBits=bits;
		fdef.filter.maskBits=-1;
		fdef.isSensor=false;
		
		for(MapObject object:objects){
			if(object instanceof RectangleMapObject){
				Rectangle rect=((RectangleMapObject) object).getRectangle();
				bdef.position.set(rect.x/PPM,rect.x/PPM);
				PolygonShape shape=((PolygonShape)new PolygonShape());
				shape.setAsBox(rect.width/2/PPM, rect.height/PPM);
				fdef.shape=shape;
				body=world.createBody(bdef);
				body.createFixture(fdef).setUserData(layerName);
				shape.dispose();
			}else if(object instanceof CircleMapObject){
				//get circle shape and set position:
				Circle circ=((CircleMapObject) object).getCircle();
				bdef.position.set(circ.x,circ.y);
				
				//set circle shape:
				CircleShape shape=new CircleShape();
				shape.setRadius(circ.radius);
				fdef.shape=shape;
				
				//create body with this polygon
				body=world.createBody(bdef);
				body.createFixture(fdef).setUserData(layerName);
				shape.dispose();
			}else if(object instanceof PolygonMapObject){
				//get polygon shape and set position:
				Polygon poly=((PolygonMapObject) object).getPolygon();
				bdef.position.set(poly.getX()/PPM,poly.getY()/PPM);
				
				//set polygon shape:
				PolygonShape shape=new PolygonShape();
				float[] polyVertices=poly.getVertices();
				for(int i=0;i<polyVertices.length;i++) polyVertices[i]=polyVertices[i]/PPM; // scale it down
				shape.set(poly.getVertices()); fdef.shape=shape;
				
				//create body with this polygon
				body=world.createBody(bdef);
				body.createFixture(fdef).setUserData(layerName);
				shape.dispose();
			}else if(object instanceof PolylineMapObject){
				//get polyline shape and set position:
				Polyline poly=((PolylineMapObject) object).getPolyline();
				bdef.position.set(poly.getX()/PPM,poly.getY()/PPM);
				
				//set polyline shape:
				ChainShape shape=new ChainShape();
				float[] polyVertices=poly.getVertices();
				for(int i=0;i<polyVertices.length;i++) polyVertices[i]=polyVertices[i]/PPM; // scale it down
				shape.createChain(poly.getVertices()); fdef.shape=shape;
				
				//create body with this polyline
				body=world.createBody(bdef);
				body.createFixture(fdef).setUserData(layerName);
				shape.dispose();
			}else if(object instanceof EllipseMapObject){
				//get ellipse shape and set position:
				Ellipse elli=((EllipseMapObject) object).getEllipse();
				bdef.position.set(elli.x/PPM,elli.y/PPM);
				
				//set ellipse shape:
				CircleShape shape=new CircleShape();
				shape.setRadius(elli.width/2/PPM);
				fdef.shape=shape;
				//create body with this ellipse
				body=world.createBody(bdef);
				body.createFixture(fdef).setUserData(layerName);
				shape.dispose();
			}
		}
	}

}