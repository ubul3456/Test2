/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ANTLR_C_Functions;

import ANTLR_Rules.CParser;
import ANTLR_Rules.CBaseListener;

import java.util.ArrayList;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author X
 */
public class ANTLR_C_Functions {
    /*plusz osztály: Function definition finder, listát ad vissza
     */

    public static void cFileParsing(CParser parser) {


        //Counters to follow the in and out movements from the nodes
        final Counter state = new Counter();
        final Counter functionDefinition = new Counter();
        final Counter declarationSpecifiers = new Counter();
        final Counter declarator = new Counter();
        final Counter compoundStatement = new Counter();
        final Counter pointer = new Counter();
        final Counter directDeclarator = new Counter();



        // The Walker walks on the tree    
        ParseTreeWalker.DEFAULT.walk(new CBaseListener() {
            //Override all the necessary functions
            @Override
            public void enterFunctionDefinition(CParser.FunctionDefinitionContext ctx) {
                //System.out.println(ctx.getChildCount());
                //state = 1 means, that we are on the FunctionDefinition node
                state.set(1);//We found a FunctionDef
                functionDefinition.inc();
                declarationSpecifiers.set(0);
                declarator.set(0);
                pointer.set(0);
                directDeclarator.set(0);

            }

            @Override
            public void exitFunctionDefinition(CParser.FunctionDefinitionContext ctx) {
                functionDefinition.dec();
                //Escape from the function, and writing on the console
                if (functionDefinition.get() == 0) {
                    Out.commandLineOutput();
                }
            }

            @Override
            public void enterDeclarationSpecifiers(CParser.DeclarationSpecifiersContext ctx) {
                declarationSpecifiers.inc();
                //state = 2 means, that we under the DeclarationSpecifiers node 
                if (state.get() == 1) {
                    state.set(2);//Under DeclarationSpecifiers

                    ArrayList<String> tokens = new ArrayList<>();
                    int i;
                    for (i = 0; i < ctx.getChildCount(); i++) {
                        LeavesFinder leaves = new LeavesFinder();
                        leaves.getLeaves(ctx.getChild(i));
                        tokens.addAll(leaves.leaves);
                    }

                    for (String s : tokens) {
                        Out.returnType = Out.returnType + " " + s;
                    }
//                        System.out.println(Out.returnType);
                }
            }

            @Override
            public void exitDeclarationSpecifiers(CParser.DeclarationSpecifiersContext ctx) {
                declarationSpecifiers.dec();
                //state = 1 means, that we are back under the FunctionDefinition node
                if (state.get() == 2) {
                    state.set(1);
                }

            }

            @Override
            public void enterDeclarator(CParser.DeclaratorContext ctx) {
                declarator.inc();
                if (state.get() < 3 && declarationSpecifiers.get() == 0) {
                    state.set(3);//We reached the Declarator field
                    //The first one
                }

            }

            @Override
            public void exitDeclarator(CParser.DeclaratorContext ctx) {
                declarator.dec();

                if (state.get() == 6 && declarator.get() == 0) {
                    state.set(7);
                    Out.paramName.addAll(ParamFinder.paramName);
                    Out.paramType.addAll(ParamFinder.paramType);

                    if (ParamFinder.number == 3) {
                        Out.paramName.add(" ...");
                        Out.paramType.add(" ...");
                    }
                }
            }

            @Override
            public void enterDirectDeclarator(CParser.DirectDeclaratorContext ctx) {
                directDeclarator.inc();
                if (state.get() == 3 && directDeclarator.get() == 1) {
                    state.set(4);
                    //We start to search for the name, it will be under 
                    //the DirectDeclarator name                        
                }
                //The first identifier will be the other part of the function name
                if (state.get() == 4) {
                    if (ctx.Identifier() != null) {
                        state.set(5);
                        //We have found the Name, and the next state will be at parameterTypeList
                        Out.functionName = " " + ctx.Identifier().getText();
                    }
                }

                if (state.get() == 6) {
                    if (ctx.Identifier() != null) {
                        //We have found one paramName
                        ParamFinder.paramName.add(" " + ctx.Identifier().getText());
                        ParamFinder.paramType.add(ParamFinder.tempType);
                        ParamFinder.tempType = new String();
                    }
                }
            }

            @Override
            public void enterPointer(CParser.PointerContext ctx) {
                pointer.inc();
                if (state.get() == 3 || state.get() == 6 && pointer.get() == 1) {
                    ArrayList<String> tokens = new ArrayList<>();
                    int i;
                    for (i = 0; i < ctx.getChildCount(); i++) {
                        LeavesFinder leaves = new LeavesFinder();
                        leaves.getLeaves(ctx.getChild(i));
                        tokens.addAll(leaves.leaves);
                    }
                    if (state.get() == 3) {
                        for (String s : tokens) {
                            Out.returnType = Out.returnType + " " + s;
                        }
                    } else if (state.get() == 6) {
                        for (String s : tokens) {
                            ParamFinder.tempType = ParamFinder.tempType + " " + s;
                        }
                    }
                }
            }

            @Override
            public void exitPointer(CParser.PointerContext ctx) {
                pointer.dec();

            }

            @Override
            public void enterParameterTypeList(CParser.ParameterTypeListContext ctx) {
                //compoundStatement.inc();
                if (state.get() == 5) {
                    state.set(6);

                    //Infinite argument
                    if (ctx.getChildCount() == 3) {
                        ParamFinder.number = 3;
                    }
                }
            }

            @Override
            public void enterStorageClassSpecifier(CParser.StorageClassSpecifierContext ctx) {
                if (state.get() == 6) {
                    LeavesFinder loaf = new LeavesFinder();
                    loaf.getLeaves(ctx);
                    for (String s : loaf.leaves) {
                        ParamFinder.tempType = ParamFinder.tempType + " " + s;
                    }
                }
            }

            @Override
            public void enterTypeSpecifier(CParser.TypeSpecifierContext ctx) {
                if (state.get() == 6) {
                    LeavesFinder loaf = new LeavesFinder();
                    loaf.getLeaves(ctx);
                    for (String s : loaf.leaves) {
                        ParamFinder.tempType = ParamFinder.tempType + " " + s;
                    }
                }
            }

            @Override
            public void enterTypeQualifier(CParser.TypeQualifierContext ctx) {
                if (state.get() == 6) {
                    LeavesFinder loaf = new LeavesFinder();
                    loaf.getLeaves(ctx);
                    for (String s : loaf.leaves) {
                        ParamFinder.tempType = ParamFinder.tempType + " " + s;
                    }
                }
            }

            @Override
            public void enterFunctionSpecifier(CParser.FunctionSpecifierContext ctx) {
                if (state.get() == 6) {
                    LeavesFinder loaf = new LeavesFinder();
                    loaf.getLeaves(ctx);
                    for (String s : loaf.leaves) {
                        ParamFinder.tempType = ParamFinder.tempType + " " + s;
                    }
                }
            }

            @Override
            public void enterAlignmentSpecifier(CParser.AlignmentSpecifierContext ctx) {
                if (state.get() == 6) {
                    LeavesFinder loaf = new LeavesFinder();
                    loaf.getLeaves(ctx);
                    for (String s : loaf.leaves) {
                        ParamFinder.tempType = ParamFinder.tempType + " " + s;
                    }
                }
            }

            @Override
            public void enterParameterDeclaration(CParser.ParameterDeclarationContext ctx) {
            }

            @Override
            public void enterCompoundStatement(CParser.CompoundStatementContext ctx) {
                compoundStatement.inc();
                //We didn't find the ParameterTypeList 
                if (state.get() == 5) {
                    state.set(7);
                }
            }

            @Override
            public void exitCompoundStatement(CParser.CompoundStatementContext ctx) {
                compoundStatement.dec();
            }

            @Override
            public void enterGccDeclaratorExtension(CParser.GccDeclaratorExtensionContext ctx) {
                compoundStatement.inc();
                //We didn't find the ParameterTypeList 
                if (state.get() == 5) {
                    state.set(7);
                }
            }
        }, parser.compilationUnit());
        
    }
}
