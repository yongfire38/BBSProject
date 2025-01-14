package egovframework.com.cop.bbs.bbs.config;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.fdl.idgnr.impl.EgovTableIdGnrServiceImpl;
import org.egovframe.rte.fdl.idgnr.impl.strategy.EgovIdGnrStrategyImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class IdGnrConfig {

    private final DataSource dataSource;

    public IdGnrConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean(name = "blogIdStrategy")
    public EgovIdGnrStrategyImpl blogIdStrategy() {
        EgovIdGnrStrategyImpl strategy = new EgovIdGnrStrategyImpl();
        strategy.setPrefix("BLOG_");
        strategy.setCipers(15);
        strategy.setFillChar('0');
        return strategy;
    }

    @Bean(name = "egovBlogIdGnrService")
    public EgovIdGnrService egovBlogIdGnrService(@Qualifier("blogIdStrategy") EgovIdGnrStrategyImpl blogIdStrategy) {
        EgovTableIdGnrServiceImpl idGnrService = new EgovTableIdGnrServiceImpl();
        idGnrService.setDataSource(dataSource);
        idGnrService.setStrategy(blogIdStrategy);
        idGnrService.setBlockSize(10);
        idGnrService.setTable("COMTECOPSEQ");
        idGnrService.setTableName("BLOG_ID");
        return idGnrService;
    }

    @Bean(name = "bbsMstrStrategy")
    public EgovIdGnrStrategyImpl bbsMstrStrategy() {
        EgovIdGnrStrategyImpl strategy = new EgovIdGnrStrategyImpl();
        strategy.setPrefix("BBSMSTR_");
        strategy.setCipers(12);
        strategy.setFillChar('0');
        return strategy;
    }

    @Bean(name = "egovBBSMstrIdGnrService")
    public EgovIdGnrService egovBBSMstrIdGnrService(@Qualifier("bbsMstrStrategy") EgovIdGnrStrategyImpl bbsMstrStrategy) {
        EgovTableIdGnrServiceImpl idGnrService = new EgovTableIdGnrServiceImpl();
        idGnrService.setDataSource(dataSource);
        idGnrService.setStrategy(bbsMstrStrategy);
        idGnrService.setBlockSize(10);
        idGnrService.setTable("COMTECOPSEQ");
        idGnrService.setTableName("BBS_ID");
        return idGnrService;
    }

    @Bean(name = "nttIdStrategy")
    public EgovIdGnrStrategyImpl nttIdStrategy() {
        EgovIdGnrStrategyImpl strategy = new EgovIdGnrStrategyImpl();
        strategy.setCipers(20);
        return strategy;
    }

    @Bean(name = "egovArticleIdGnrService")
    public EgovIdGnrService egovNttIdGnrService(@Qualifier("nttIdStrategy")EgovIdGnrStrategyImpl nttIdStrategy){
        EgovTableIdGnrServiceImpl idGnrService = new EgovTableIdGnrServiceImpl();
        idGnrService.setDataSource(dataSource);
        idGnrService.setStrategy(nttIdStrategy);
        idGnrService.setBlockSize(10);
        idGnrService.setTable("COMTECOPSEQ");
        idGnrService.setTableName("NTT_ID");
        return idGnrService;
    }

    @Bean
    public EgovIdGnrStrategyImpl fileStrategy(){
        EgovIdGnrStrategyImpl strategy = new EgovIdGnrStrategyImpl();
        strategy.setPrefix("FILE_");
        strategy.setCipers(15);
        strategy.setFillChar('0');
        return strategy;
    }

    @Bean(name = "egovFileIdGnrService")
    public EgovIdGnrService egovFileIdGnrService(@Qualifier("fileStrategy")EgovIdGnrStrategyImpl fileStrategy){
        EgovTableIdGnrServiceImpl idGnrService = new EgovTableIdGnrServiceImpl();
        idGnrService.setDataSource(dataSource);
        idGnrService.setStrategy(fileStrategy);
        idGnrService.setBlockSize(10);
        idGnrService.setTable("COMTECOPSEQ");
        idGnrService.setTableName("FILE_ID");
        return idGnrService;
    }

    @Bean
    public EgovIdGnrStrategyImpl answerNoStrategy(){
        EgovIdGnrStrategyImpl strategy = new EgovIdGnrStrategyImpl();
        strategy.setCipers(20);
        return strategy;
    }

    @Bean(name = "egovAnswerNoGnrService")
    public EgovIdGnrService egovAnswerNoGnrService(@Qualifier("answerNoStrategy")EgovIdGnrStrategyImpl answerNoStrategy){
        EgovTableIdGnrServiceImpl idGnrService = new EgovTableIdGnrServiceImpl();
        idGnrService.setDataSource(dataSource);
        idGnrService.setStrategy(answerNoStrategy);
        idGnrService.setBlockSize(10);
        idGnrService.setTable("COMTECOPSEQ");
        idGnrService.setTableName("ANSWER_NO");
        return idGnrService;
    }

    @Bean
    public EgovIdGnrStrategyImpl stsfdgNoStrategy(){
        EgovIdGnrStrategyImpl strategy = new EgovIdGnrStrategyImpl();
        strategy.setCipers(20);
        return strategy;
    }

    @Bean(name = "egovStsfdgNoGnrService")
    public EgovIdGnrService egovStsfdgNoGnrService(@Qualifier("stsfdgNoStrategy")EgovIdGnrStrategyImpl stsfdgNoStrategy){
        EgovTableIdGnrServiceImpl idGnrService = new EgovTableIdGnrServiceImpl();
        idGnrService.setDataSource(dataSource);
        idGnrService.setStrategy(stsfdgNoStrategy);
        idGnrService.setBlockSize(10);
        idGnrService.setTable("COMTECOPSEQ");
        idGnrService.setTableName("STSFDG_NO");
        return idGnrService;
    }
    
    @Bean(name = "syncIdStrategy")
    public EgovIdGnrStrategyImpl syncIdStrategy() {
        EgovIdGnrStrategyImpl strategy = new EgovIdGnrStrategyImpl();
        strategy.setPrefix("SYNC_");
        strategy.setCipers(15);
        strategy.setFillChar('0');
        return strategy;
    }
    
    @Bean(name = "egovSyncIdGnrService")
	public EgovTableIdGnrServiceImpl egovSyncIdGnrService(@Qualifier("syncIdStrategy") EgovIdGnrStrategyImpl syncIdStrategy) {
    	EgovTableIdGnrServiceImpl idGnrService = new EgovTableIdGnrServiceImpl();
    	idGnrService.setDataSource(dataSource);
    	idGnrService.setStrategy(syncIdStrategy);
    	idGnrService.setBlockSize(10);
    	idGnrService.setTable("COMTECOPSEQ");
    	idGnrService.setTableName("SYNC_ID");
		
		return idGnrService;
	}
}
