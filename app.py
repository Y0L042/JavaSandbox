import sys
import os
import subprocess
import shutil

PROJECT_DIR = "./"

def compile_java_library_to_jar(src_dir, bin_dir, jar_name, classpath=None):
    # Ensure the temporary bin directory exists
    temp_bin_dir = os.path.join(bin_dir, "temp", jar_name)
    os.makedirs(temp_bin_dir, exist_ok=True)
    
    # Compile .java files with warnings suppressed
    javac_command = ["javac", "-Xlint:none", "-d", temp_bin_dir]
    if classpath:
        javac_command.extend(["-cp", classpath])
    for root, dirs, files in os.walk(src_dir):
        for file in files:
            if file.endswith(".java"):
                java_file_path = os.path.join(root, file)
                javac_command.append(java_file_path)
    subprocess.run(javac_command, check=True)
    
    # Create a .jar file from compiled .class files
    jar_path = os.path.join(bin_dir, jar_name)
    subprocess.run(["jar", "cvf", jar_path, "-C", temp_bin_dir, "."], check=True)
    
    # Clean up the temporary bin directory
    shutil.rmtree(temp_bin_dir)

def compile_java_sources(src_dir, lib_dir, bin_dir):
    # Compile stdlib into stdlib.jar
    stdlib_src_dir = os.path.join(lib_dir, "stdlib")
    compile_java_library_to_jar(stdlib_src_dir, bin_dir, "stdlib.jar")

    # Compile algs4 into algs4.jar, including stdlib.jar in the classpath
    algs4_src_dir = os.path.join(lib_dir, "algs4")
    stdlib_jar_path = os.path.join(bin_dir, "stdlib.jar")
    compile_java_library_to_jar(algs4_src_dir, bin_dir, "algs4.jar", classpath=stdlib_jar_path)

# def compile_app(src_dir, bin_dir, lib_dir):
#     # Compile App.java with warnings suppressed, adjusting classpath to include the bin directory and the created .jar files
#     jar_files = ":".join([os.path.join(bin_dir, f) for f in os.listdir(bin_dir) if f.endswith(".jar")])
#     classpath = f"{bin_dir}:{jar_files}" if jar_files else bin_dir
#     subprocess.run(["javac", "-Xlint:none", "-cp", classpath, "-d", bin_dir, os.path.join(src_dir, "*.java")], check=True)

def compile_app(src_dir, bin_dir, lib_dir):
    # Initialize the javac command with options
    javac_command = ["javac", "-Xlint:none"]

    # Set the classpath to include the bin directory and any .jar files
    jar_files = ":".join([os.path.join(bin_dir, f) for f in os.listdir(bin_dir) if f.endswith(".jar")])
    classpath = f"{bin_dir}:{jar_files}" if jar_files else bin_dir
    javac_command.extend(["-cp", classpath])

    # Specify the output directory for compiled .class files
    javac_command.extend(["-d", bin_dir])

    # Walk through the src_dir to find and compile all .java files
    for root, dirs, files in os.walk(src_dir):
        for file in files:
            if file.endswith(".java"):
                java_file_path = os.path.join(root, file)
                javac_command.append(java_file_path)
                
    print("\n" + " ".join(javac_command) + "\n")
    
    # Compile all .java files found in src_dir
    subprocess.run(javac_command, check=True)

def run_app(class_name, bin_dir, lib_dir, args):
    jar_files = ":".join([os.path.join(bin_dir, f) for f in os.listdir(bin_dir) if f.endswith(".jar")])
    classpath = f"{bin_dir}:{jar_files}" if jar_files else bin_dir
    command = ["java", "-cp", classpath, class_name] + args
    
    print(" " + " ".join(command) + "\n")
    
    subprocess.run(command, check=True)

def main():
    project_dir = PROJECT_DIR
    src_dir = os.path.join(project_dir, "src")
    lib_dir = os.path.join(project_dir, "lib")
    bin_dir = os.path.join(project_dir, "bin")

    # Ensure the output directory exists
    os.makedirs(bin_dir, exist_ok=True)

    if len(sys.argv) > 1 and sys.argv[1] == "complibs":
        # Compile Java libraries into .jar files
        compile_java_sources(src_dir, lib_dir, bin_dir)
    else:
        # Compile and run the application with all warnings suppressed, passing along additional arguments
        compile_app(src_dir, bin_dir, lib_dir)
        run_app("App", bin_dir, lib_dir, sys.argv[1:])

if __name__ == "__main__":
    main()
