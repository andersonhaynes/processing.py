package jycessing.mode.export;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import jycessing.mode.PyEditor;
import jycessing.mode.PythonMode;
import processing.app.Base;
import processing.app.Library;
import processing.app.Preferences;
import processing.app.Sketch;
import processing.core.PApplet;
import processing.core.PConstants;


/**
 * 
 * A Mac export.
 * TODO implement.
 * 
 * If we embed java, we embed Processing's java, since we know it's been properly appbundled
 * and all symlinks work and stuff.
 * 
 * Layout:
 * $appdir/
 *        /$sketch.app/Contents/
 *              /MacOS/
 *                    /$sketch (shell script that cd's to ../Processing and runs the sketch)
 *              /Info.plist (holds os x stuff)
 *              /Resources/
 *                    /sketch.icns (pretty icon)
 *              /Processing/
 *                    /source/
 *                    /lib/
 *                    /code/
 *                    /data/
 *              /PlugIns/jdk$version.jdk/ (copied from core processing)
 *                     
 *
 */
public class MacExport extends PlatformExport {
  
  @Override
  protected void log(final String msg) {
    if (PythonMode.VERBOSE) {
      System.err.println(MacExport.class.getSimpleName() + ": " + msg);
    }
  }
  
  public MacExport(Sketch sketch, PyEditor editor, Set<Library> libraries) {
    this.id = PConstants.MACOSX;
    this.name = PConstants.platformNames[id];
    this.sketch = sketch;
    this.editor = editor;
    this.libraries = libraries;
    this.arch = Arch.AMD64;
  }
  
  /**
   * It only makes sense to copy the JDK when we're on a Mac, running Processing from a .app bundle.
   */
  public void copyJDKPlugin () {
    
  }
  
  @Override
  public void export() throws IOException {
    // Work out user preferences and other possibilities we care about
    final boolean embedJava =
        (id == PApplet.platform) && Preferences.getBoolean("export.application.embed_java");
    final boolean setMemory = Preferences.getBoolean("run.options.memory");
    final boolean presentMode = Preferences.getBoolean("export.application.fullscreen");
    final boolean stopButton = Preferences.getBoolean("export.application.stop") && presentMode;
    
    // Work out the folders we'll be (maybe) using
    final File destFolder = new File(sketch.getFolder(), "application." + name);
    final File appRootFolder = new File(destFolder, sketch.getName()+".app");
    final File contentsFolder = new File(appRootFolder, "Contents");
    final File binFolder = new File(contentsFolder, "MacOS");
    final File resourcesFolder = new File(contentsFolder, "Resources");
    final File processingFolder = new File(contentsFolder, "Processing");
    
    final File infoPlist = new File(contentsFolder, "Info.plist");
    final File script = new File(binFolder, sketch.getName());
    final File icon = new File(resourcesFolder, sketch.getName()+".icns");
    
    copyBasicStructure(processingFolder);
    
    if (embedJava) {
      copyJDKPlugin();
    }
    
    
    
    log("I would do a mac export here... if I knew how :(");

  }

}
