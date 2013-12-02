import static java.lang.Math.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.Serializable;

interface Cell {
	void update(Board b, Location l);
	Location addLocation(Board b, Location l);
	boolean doGrow(Location l, Board b);
	Cell protoypeCell();
}

// put some logic that's common across multiple Cell implementations
// in an abstract class. 
abstract class AbstractCell implements Cell {

  // uses the template pattern - doGrow, addLocation are defined by subclasses
	public void update(Board b, Location l) {
		if (doGrow(l, b)) {
			Location loc = addLocation(b, l);
      System.out.println("Adding: " + this.getClass() + " to " + loc );
			b.add(loc, protoypeCell());
		}
	}

}

// don't embed coordinates within cell, instead keep with board
class WhiteCell extends AbstractCell {

	private static DirectionComputer dc = new UniformRandomDirectionComputer();
  private static WhiteCell WhiteCellSingletonObject = new WhiteCell();

  public WhiteCell() {
  }

	public Location addLocation(Board b, Location l) {
		return dc.direction(b, l);
	}

  // grow if there are more white cells than germs cells
	public boolean doGrow(Location l, Board b) {
    int w = b.countCell(l, WhiteCell.class);
    int g = b.countCell(l, GermCell.class);
		boolean result = (w > g);
    System.out.println("result from doGrow = " + result );
    return result;
	}

	public Cell protoypeCell() {
		return WhiteCellSingletonObject;
	}
	
	@Override 
	public String toString() {
		return "wc";
	}

}

// decorator pattern, adds a count of the number of calls to grow function for WhiteCell
class InstrumentedWhiteCell implements Cell {
  private static Cell c = new WhiteCell().protoypeCell();
  private static int numGrows = 0;
  private static Cell InstrumentedWhiteCellSingletonObject = new InstrumentedWhiteCell();

  public InstrumentedWhiteCell() {
  }

	public void update(Board b, Location l) {
    c.update(b, l);
  }

	public Location addLocation(Board b, Location l) {
    return c.addLocation(b, l);
  }

	public boolean doGrow(Location l, Board b) {
    numGrows++;
    return c.doGrow(l, b);
  }

  public static int numCallsToDoGrow() {
    return numGrows;
  }

  public Cell protoypeCell() {
    return InstrumentedWhiteCellSingletonObject;
  }

  @Override 
  public String toString() {
    return "iwc";
  }

}


class GermCell extends AbstractCell {

	private static DirectionComputer dc = new TopBottomLeftRightDirectionComputer();
  private static GermCell GermCellSingletonObject = new GermCell();

  public GermCell() {
  }

	public Location addLocation(Board b, Location l) {
		return dc.direction(b, l);
	}

  // grow if there are at least 2 more germs than white cells
	public boolean doGrow(Location l, Board b) {
    int w = b.countCell(l, WhiteCell.class);
    int g = b.countCell(l, GermCell.class);
		boolean result = (g - w) >= 2;
    System.out.println("result from doGrow = " + result );
    return result;
	}

	public Cell protoypeCell() {
		return GermCellSingletonObject;
	}
	
	@Override 
	public String toString() {
		return "gc";
	}

}

// null pattern, used to indicate empty cells in a board
class NullCell extends AbstractCell {

  public NullCell() {
  }
  
  private static NullCell NullCellSingletonObject = new NullCell();

  public static NullCell getNullCellSingleton() {
    return NullCellSingletonObject;
  }

	public Location addLocation(Board b, Location l) {
		throw new IllegalStateException("addLocation() called from NullCell");
	}

	public boolean doGrow(Location l, Board b) {
		return false;
	}

	public Cell protoypeCell() {
		return getNullCellSingleton();
	}
	
	@Override 
	public String toString() {
		return "nc";
	}
}

// legacy class, does not support the Cell interface
class Virus {

  private static Random r = new java.util.Random(0);

	public Virus() {
	}

	private static Virus singltonVirus = new Virus();

	public static Virus getVirus() {
	  return singltonVirus;
	}

  // virus grows randomly
	Location addLocation(Board b) {
     List<Location> allEmptyLocations = new ArrayList<Location>();
     for ( int i = 0; i < b.getState().length; i++ ) {
       for ( int j = 0; j < b.getState()[0].length; j++ ) {
         if ( b.getState()[i][j] == NullCell.getNullCellSingleton() ) {
           allEmptyLocations.add( new Location( i, j ) );
         }
       }
     }
     return (allEmptyLocations.size() == 0)
							? null 
							: allEmptyLocations.get( r.nextInt( allEmptyLocations.size() ) );
  }
}

// use an object adapter to make Virus fit with Cell interface
class VirusAdapter implements Cell {
  static Virus v = Virus.getVirus();

  public void update(Board b, Location l) {
    if ( addLocation( b, l ) != null ) {
		  b.add(l, this);
    }
  }

	public Location addLocation(Board b, Location l) {
	  // essentially, this is a forwarded call
	  return v.addLocation(b);
	}

  public boolean doGrow(Location l, Board b) {
    throw new UnsupportedOperationException("virus doGrow");
  }

  public Cell protoypeCell() {
    return new VirusAdapter();
  }

  @Override
  public String toString() {
    return "v ";
  }

}

// different cells grow in different directions
interface DirectionComputer {
	public Location direction(Board b, Location l);
}

// selects an empty adjacent cell to grow to uniformly at random
class UniformRandomDirectionComputer implements DirectionComputer {
	private static Random rnd = new Random();

	public Location direction(Board b, Location loc) {
		List<Location> locs = b.computeEmptyNeighbors(loc);
		Location result = (locs.size() > 0 ) ? locs.get(rnd.nextInt(locs.size())) : null;
		return result;
	}
}

// selects an empty adjacent cell to grow to, prefers cells from top to bottom, left to right
class TopBottomLeftRightDirectionComputer implements DirectionComputer {

	public Location direction(Board b, Location loc) {
    // computeEmptyNeighbors fills in empty cells in the top to bottom,
    // left to right order that we want
		List<Location> locs = b.computeEmptyNeighbors(loc);
		Location result = (locs.size() > 0 ) ? locs.get(0) : null;
		return result;
	}
}



// encodes a position in a board
class Location {
	private final int x;
	private final int y;

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

  @Override
  public String toString() {
    return x + "," + y;
  }
}

// provide a total order on locations (sort on Y then on X)
// not used by current implementation
class LocCmp {
	private static class InnerLocCmp implements Comparator<Location>,
			Serializable {
		public int compare(Location s1, Location s2) {
			int ydiff = s1.getY() - s2.getY();
			if (ydiff != 0) {
				return ydiff;
			}
			return s1.getX() - s2.getX();
		}
	}

  // stateless comparator object => don't want unneeded objects
	public static final Comparator<Location> LOCATION_COMPARATOR = new InnerLocCmp();
}

// encodes the state of the board
class Board {

	private Cell[][] state;

  public Cell[][] getState() {
    return state;
  }

  // constructor works off of a 2D array of cells, need
  // to copy so that we don't corrupt the array passed in
	public Board(Cell[][] initState) {
		state = new Cell[initState.length][];
		for (int i = 0; i < initState.length; i++) {
			state[i] = initState[i].clone();
		}
	}

  // count the number of cells of type cls around locations l	
	public int countCell(Location l, Class cls) {
		int xmax = state.length-1;
		int ymax = state[0].length-1;
		int result = 0;
		int x = l.getX();
		int y = l.getY();
    for ( int i = max(0, x-1); i < min(xmax, x + 1); i++ ) {
      for ( int j = max(0, y-1); j < min(ymax, y + 1); j++ ) {
        // don't count cell at location l itself
        if ( !(i == x && j == y) && state[i][j].getClass() == cls ) {
          result++;
        }
      }
    }
		return result;
	}

	public Cell getCell(int x, int y) {
		return state[x][y];
	}
	
	public void add(Location loc, Cell cell) {
    if ( loc != null ) {
		  state[loc.getX()][loc.getY()] = cell;
    }
	}

  // goes top to bottom, left to right	
	public List<Location> computeEmptyNeighbors(Location loc) {
		List<Location> result = new ArrayList<Location>(); 
    // TODO - repeats code from countCell
		int xmax = state.length-1;
		int ymax = state[0].length-1;
		int x = loc.getX();
		int y = loc.getY();
    for ( int i = max(0, x-1); i < min(xmax, x + 1); i++ ) {
      for ( int j = max(0, y-1); j < min(ymax, y + 1); j++ ) {
        if ( state[i][j].getClass() == NullCell.class ) {
          result.add( new Location(i,j) );
        }
      }
    }
    return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for ( Cell[] ca : state) {
			for (Cell c : ca) {
				sb.append(c + ":");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}

// useful when we want to change the type of cells 
interface CellFactoryInterface {
  Cell createWhite();
  Cell createGerm();
  Cell createNull();
  Cell createVirus();
}

// vanilla factory
class BasicCellFactory implements CellFactoryInterface {
  public Cell createWhite() { return new WhiteCell().protoypeCell(); }
  public Cell createGerm() { return new GermCell().protoypeCell(); }
  public Cell createNull() { return new NullCell().protoypeCell(); }
  public Cell createVirus() { return new VirusAdapter().protoypeCell(); }
}

// factory that uses instrumented white cells
class InstrumentedCellFactory extends BasicCellFactory implements CellFactoryInterface {
  public Cell createWhite() { return new InstrumentedWhiteCell(); }
}


public class Patterns {

  // top level driver, performs a number of simulations of the board
	public static void main(String[] args) {
    CellFactoryInterface cf = new InstrumentedCellFactory();
		Cell wc = cf.createWhite();
		Cell gc = cf.createGerm();
		Cell nc = cf.createNull();
		Cell vc = cf.createVirus();
		Cell[][] initState = { { nc, wc, wc, gc }, { nc, wc, vc, gc }, { nc, gc, gc, gc } };
		Board board = new Board(initState);
		int count = 3;
		while (count-- > 0) {
			System.out.println(board);
			for (int i = 0; i < initState.length; i++) {
				for (int j = 0; j < initState[i].length; j++) {
					board.getCell(i, j).update(board, new Location(i, j));
				}
			}
		}
    System.out.println("Number of calls to white cell do grows = " + InstrumentedWhiteCell.numCallsToDoGrow() );
	}

}
