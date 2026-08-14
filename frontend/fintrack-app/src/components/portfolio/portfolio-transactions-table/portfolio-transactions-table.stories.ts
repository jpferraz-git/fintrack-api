import type { Meta, StoryObj } from '@storybook/angular';
import { PortfolioTransactionsTable } from './portfolio-transactions-table.component';

const meta: Meta<PortfolioTransactionsTable> = {
  title: 'Components/PortfolioTransactionsTable',
  component: PortfolioTransactionsTable,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<PortfolioTransactionsTable>;

export const Primary: Story = {
  args: {},
};
